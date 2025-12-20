package com.planchella.routes;


import com.planchella.Services.AttachmentService;
import com.planchella.domain.AttachmentMetadata;
import com.planchella.enums.MimeType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentRoutes {

    @Autowired
    AttachmentService attachmentService;
    /**
     * used if we want to choose speed at the price of reliability.
     * we can have non-transactional uploads where the frontend sends the email and starts sending the files,
     * but we consider the data to be existent once it got acknowledged by the attachment service,
     * which follows the principle of optimistic response.
     */
    @GetMapping("/ids")
    public ResponseEntity<?> getValidAttachmentId(){

        try {
            Map<String,String> response = new HashMap<>();
            response.put("id",attachmentService.generateAttachmentId(true));

            return ResponseEntity.ok().body(response);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "can't add event"
            ));
        }
    }

    @GetMapping("/{attachment_id}")
    public ResponseEntity<?> getAttachment(@PathVariable("attachment_id") String attachmentId,
                                           HttpServletResponse response, Authentication authentication){
        try {
            String username = authentication.getName();

            AttachmentMetadata metadata = attachmentService.getAttachmentMetadata(attachmentId);
            InputStream inputStream = attachmentService.getAttachmentStream(attachmentId);
            MimeType type = metadata.getMimeType();

            response.setContentType(type.toString());

            OutputStream outputStream = response.getOutputStream();
            inputStream.transferTo(outputStream);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> saveAttachment(@RequestParam(required = false) String id,
                                            @RequestParam String fileName, HttpServletRequest request){
        try {
             String mimeStr= request.getHeader("Content-Type");
             String extStr = fileName.split("\\.")[1];
             
             long size = request.getContentLengthLong();
             if (size > 10 * 1024 * 1024){
                 return new ResponseEntity<>("You can't upload a file of size more than 10MBs", HttpStatus.BAD_REQUEST);
             }

             MimeType mimeType = MimeType.fromValue(mimeStr);
             

             if (mimeType != MimeType.fromFileExtension(extStr)){
                 return new ResponseEntity<>(HttpStatus.CONFLICT);
             }

             AttachmentMetadata data;

             data = (id == null || id.trim().isEmpty())?
                     attachmentService.saveAttachment(mimeType, fileName, request.getInputStream()):
                     attachmentService.saveAttachment(id, mimeType, fileName, request.getInputStream());

            if (data == null){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{attachment_id}")
    public void  deleteAttachment(@PathVariable("attachment_id") String attachmentId) {

    }


}
