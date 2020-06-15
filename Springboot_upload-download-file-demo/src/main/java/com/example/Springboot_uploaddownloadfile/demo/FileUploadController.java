package com.example.Springboot_uploaddownloadfile.demo;

import java.awt.List;
import java.nio.file.FileStore;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
	public class FileUploadController {

	    @Autowired
	    private FileStore fileStorageService;

	    @PostMapping("/uploadFile")
	    public Response uploadFile(@RequestParam("file") MultipartFile file) {
	        String fileName = ((Object) fileStorageService).storeFile(file);

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	            .path("/downloadFile/")
	            .path(fileName)
	            .toUriString();

	        return new Response();
	    }

	    @PostMapping("/uploadMultipleFiles")
	    public List uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
	        MultipartFile file;
			return Arrays.asList(files)
	            .stream()
	            .map((Response) file - > uploadFile(file))
	            .collect(Collectors.toList());
	    }
	}

