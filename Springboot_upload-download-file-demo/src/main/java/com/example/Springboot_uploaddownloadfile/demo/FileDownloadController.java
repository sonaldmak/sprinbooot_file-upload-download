package com.example.Springboot_uploaddownloadfile.demo;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.FileStore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sun.org.slf4j.internal.LoggerFactory;

import ch.qos.logback.classic.Logger;

@RestController
public class FileDownloadController {
	
    private static final com.sun.org.slf4j.internal.Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    @Autowired
    private FileStore fileStorageService;

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity < Resource > downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = ((Object) fileStorageService).loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(((Object) resource).getFile().getAbsolutePath());
        } catch (IOException ex) {
            ((Logger) logger).info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
}
