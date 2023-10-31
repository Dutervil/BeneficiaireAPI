package com.wd.api.resource;

import com.wd.api.domain.Dossier;
import com.wd.api.repository.DossierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/dossier")
@CrossOrigin("*")
public class DossierResource {

    public final DossierRepository dossierRepository;

    public DossierResource(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("userId") String userId,
                                                 @RequestParam("title") String title) {
        try {
            Dossier document = new Dossier();
            document.setFileData(file.getBytes());
            document.setUserId(userId);
            document.setTitle(title);
            dossierRepository.save(document);
            return ResponseEntity.ok("Document uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the document.");
        }
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> get(@PathVariable("userId") Long userId) {
        try {

            return ResponseEntity.ok(dossierRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the document.");
        }
    }
}
