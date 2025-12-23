package com.planchella.Services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.google.auth.Credentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

import com.planchella.domain.Event;
import com.planchella.utils.DateUtils;
import jakarta.activation.DataHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class GoogleIntegrations {

    Gmail service = null;
    final String fromEmail = "nour.atawy2015@gmail.com";

    public GoogleIntegrations() throws IOException {
        InputStream in = GoogleIntegrations.class.getClassLoader().getResourceAsStream("client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(), new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                clientSecrets,
                Collections.singleton(GmailScopes.GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("online")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        this.service = new Gmail.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("Planchella")
                .build();

    }

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param toEmailAddress - Email address of the recipient
     * @throws MessagingException - if a wrongly formatted address is encountered.
     * @throws IOException        - if service account credentials file not found.
     */

    public Message sendEmail(String toEmailAddress, String subject, String body, String icsContent)
            throws MessagingException, IOException {

        // Encode as MIME message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        MimeMultipart multipart = new MimeMultipart("mixed");

        MimeMultipart alternativePart = new MimeMultipart("alternative");
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(body, "text/html; charset=utf-8");
        alternativePart.addBodyPart(htmlPart);

        MimeBodyPart alternativeBodyPart = new MimeBodyPart();
        alternativeBodyPart.setContent(alternativePart);
        multipart.addBodyPart(alternativeBodyPart);

        if (icsContent != null) {
            MimeBodyPart calendarPart = new MimeBodyPart();
            calendarPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
            calendarPart.setHeader("Content-ID", "calendar_display");
            calendarPart.setDataHandler(new DataHandler(
                    new ByteArrayDataSource(icsContent, "text/calendar;method=REQUEST;name=\"invite.ics\"")));
            multipart.addBodyPart(calendarPart);
        }

        email.setFrom(new InternetAddress(this.fromEmail));
        email.addRecipient(jakarta.mail.Message.RecipientType.TO,
                new InternetAddress(toEmailAddress));
        email.setSubject(subject);
        email.setContent(multipart);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);

        Message message = new Message();
        message.setRaw(encodedEmail);

        try {
            message = service.users().messages().send("me", message).execute();
            System.out.println("Message id: " + message.getId());
            System.out.println(message.toPrettyString());
            return message;
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }
        return null;
    }

    public String generateIcsString(Event event, String recipientEmail) {
        // dates must be in the format: yyyyMMdd'T'HHmmss'Z'
        String start = DateUtils.cleanToIcsDate(event.getEventStartDate());
        String end = DateUtils.cleanToIcsDate(event.getEventEndDate());
        String now = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
                .format(java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC));

        return "BEGIN:VCALENDAR\r\n" +
                "VERSION:2.0\r\n" +
                "PROID:-//Planchella//Event//EN\r\n" +
                "METHOD:REQUEST\r\n" +
                "BEGIN:VEVENT\r\n" +
                "UID:" + UUID.randomUUID().toString() + "\r\n" +
                "DTSTAMP:" + now + "\r\n" +
                "ORGANIZER;CN=Planchella:mailto:" + fromEmail + "\r\n" +
                "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE;CN=" + recipientEmail + ":mailto:"
                + recipientEmail + "\r\n" +
                "DTSTART:" + start + "\r\n" +
                "DTEND:" + end + "\r\n" +
                "SUMMARY:" + event.getTitle() + "\r\n" +
                "DESCRIPTION:" + event.getDescription() + "\r\n" +
                "LOCATION:" + (event.isHasLocation() ? event.getLatitude() + "," + event.getLongitude() : "Online")
                + "\r\n" +
                "STATUS:CONFIRMED\r\n" +
                "END:VEVENT\r\n" +
                "END:VCALENDAR";
    }

    public static void main() throws MessagingException, IOException {
        GoogleIntegrations google = new GoogleIntegrations();
        google.sendEmail("nour.atawy2015@gmail.com", "test", "test", null);
    }
}