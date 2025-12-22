package com.planchella.Services;

import com.planchella.domain.Event;
import com.planchella.domain.User;
import com.planchella.entities.StarEntity;
import com.planchella.mappers.EventMapper;
import com.planchella.repositories.events.IEventRepository;
import com.planchella.repositories.events.StarRepository;
import com.planchella.repositories.users.IUserRepository;
import com.planchella.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private StarRepository starRepo;

    @Autowired
    private IEventRepository eventRepo;

    @Autowired
    private GoogleIntegrations googleIntegrations;

    public User getUser(Long userId) {
        return userRepo.getUser(userId);
    }

    public List<User> getUsers(Long communityId, int count, int offset) {
        return userRepo.getUsers(communityId, count, offset);
    }

    public List<Event> getUserEvents(Long userId, int count, int offset) {
        return eventRepo.getEventsByAuthor(userId, count, offset);
    }

    public List<Event> getStarredEvents(Long userId, int count, int offset) {
        if (count <= 0)
            return new ArrayList<>();
        Pageable pageable = PageRequest.of(offset / count, count);
        List<StarEntity> stars = starRepo.findByUserId(userId, pageable);

        return stars.stream()
                .map(star -> EventMapper.entityToDomain(star.getEvent()))
                .collect(Collectors.toList());
    }

    public void updateUser(Long userId, User newUserData) {
        User user = userRepo.getUser(userId);
        if (user != null) {
            user.updateByDelta(newUserData);
            userRepo.saveUser(user);
        }
    }

    public void addUser(User user) {
        user.setId(IdGenerator.generateId());
        userRepo.saveUser(user);
    }

    public void deleteUser(Long userId) {
        userRepo.deleteUser(userId);
    }

    public void sendEventMailToUser(Long userId, Long eventId) throws IOException {
        User user = userRepo.getUser(userId);
        Event event = eventRepo.getEvent(eventId);
        String htmlBase = Files
                .readString(Path.of(ClassLoader.getSystemResource("static/email_template.html").getPath()));
        String eventButtonHtml = Files
                .readString(Path.of(ClassLoader.getSystemResource("static/email_button_snippet.html").getPath()));
        String eventLocationHtml = Files
                .readString(Path.of(ClassLoader.getSystemResource("static/email_location_snippet.html").getPath()));
        String eventTimeHtml = Files
                .readString(Path.of(ClassLoader.getSystemResource("static/email_time_snippet.html").getPath()));

        String formattedCreationDate = com.planchella.utils.DateUtils.formatIsoDate(event.getCreationDate());

        String bodyContent = htmlBase
                .replace("{{author_name}}", user.getName())
                .replace("{{event_title}}", event.getTitle())
                .replace("{{description}}", event.getDescription())
                .replace("{{author_pic_url}}", user.getPicUrl())
                .replace("{{creation_date}}", formattedCreationDate)
                .replace("{{event_type}}", event.getEventType().toString());

        if (event.isHasLocation()) {
            eventLocationHtml = eventLocationHtml
                    .replace("{{event_location}}", event.getLatitude() + ", " + event.getLongitude());
            bodyContent = bodyContent.replace("{{location_html}}", eventLocationHtml);
        } else {
            bodyContent = bodyContent.replace("{{location_html}}", "");
        }

        if (event.isHasTime()) {

            String formattedStart = com.planchella.utils.DateUtils.formatIsoDate(event.getEventStartDate());
            String formattedEnd = com.planchella.utils.DateUtils.formatIsoDate(event.getEventEndDate());

            eventTimeHtml = eventTimeHtml
                    .replace("{{start_date}}", formattedStart)
                    .replace("{{end_date}}", formattedEnd);

            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                java.util.Map<String, Object> schemaMap = new java.util.HashMap<>();

                schemaMap.put("@context", "https://schema.org");
                schemaMap.put("@type", "Event");
                schemaMap.put("name", event.getTitle());
                schemaMap.put("startDate", event.getEventStartDate()); // Assuming these are already valid ISO strings
                schemaMap.put("endDate", event.getEventEndDate());
                schemaMap.put("description", event.getDescription());
                schemaMap.put("eventStatus", "https://schema.org/EventScheduled");

                if (event.isHasLocation()) {
                    schemaMap.put("eventAttendanceMode", "https://schema.org/OfflineEventAttendanceMode");

                    java.util.Map<String, Object> locationMap = new java.util.HashMap<>();
                    locationMap.put("@type", "Place");
                    locationMap.put("name", "Event Location"); // Fallback name

                    java.util.Map<String, Object> addressMap = new java.util.HashMap<>();
                    addressMap.put("@type", "PostalAddress");
                    addressMap.put("streetAddress", "Unknown Address"); // Fallback address
                    locationMap.put("address", addressMap);

                    java.util.Map<String, Object> geoMap = new java.util.HashMap<>();
                    geoMap.put("@type", "GeoCoordinates");
                    geoMap.put("latitude", event.getLatitude());
                    geoMap.put("longitude", event.getLongitude());
                    locationMap.put("geo", geoMap);

                    schemaMap.put("location", locationMap);
                } else {
                    schemaMap.put("eventAttendanceMode", "https://schema.org/OnlineEventAttendanceMode");

                    java.util.Map<String, Object> locationMap = new java.util.HashMap<>();
                    locationMap.put("@type", "Place");
                    locationMap.put("name", "Online Event");
                    schemaMap.put("location", locationMap);
                }

                if (event.getCustomUrl() != null && !event.getCustomUrl().isEmpty()) {
                    schemaMap.put("url", event.getCustomUrl());
                }

                String schemaJson = mapper.writeValueAsString(schemaMap);
                bodyContent = bodyContent.replace("{{schema_json}}", schemaJson);

            } catch (Exception e) {
                System.err.println("JSON-LD Generation Error: " + e.getMessage());
                bodyContent = bodyContent.replace("{{schema_json}}", "");
            }

            bodyContent = bodyContent.replace("{{time_html}}", eventTimeHtml);

        } else {
            bodyContent = bodyContent.replace("{{time_html}}", "");
            bodyContent = bodyContent.replace("{{schema_json}}", "");
        }

        if (event.getCustomUrl() != null && !event.getCustomUrl().isEmpty()) {
            eventButtonHtml = eventButtonHtml.replace("{{visit_url}}", event.getCustomUrl());
            bodyContent = bodyContent.replace("{{visit_button_html}}", eventButtonHtml);
        } else {
            bodyContent = bodyContent.replace("{{visit_button_html}}", "");
        }
        try {
            googleIntegrations.sendEmail(user.getEmail(), "Check this event out!!", bodyContent);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println(bodyContent);
        }
    }

}
