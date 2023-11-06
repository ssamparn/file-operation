package com.java.fileupload.web;

import java.io.IOException;
import java.io.InputStream;
import jakarta.servlet.http.HttpServletRequest;

import com.java.fileupload.model.UploadFileResponse;
import com.java.fileupload.web.exception.FileUploadException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    /**
     * Injected just so we can return information about it.  If Spring's multipart support is enabled in
     * {@code application.properties}, and/or we add back CommonsMultipartResolver in our configuration,
     * re-autowire this to get more information.
     */
    @Autowired
    private MultipartResolver multipartResolver;

    @PostMapping(path = "/mulitipart-file",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UploadFileResponse> uploadFileByMutipartFile(@RequestParam(required = false) MultipartFile file,
        HttpServletRequest request) throws IOException {

        if (file == null) {
            throw new FileUploadException("MultipartFile was null (likely no parts on request).  Make sure Spring's multipart support is enabled for this upload method to work");
        }

        UploadFileResponse response = createUploadFileResponse(request);

        try(InputStream inputStream = file.getInputStream()) {
            response.setInputStreamType(inputStream.getClass().getName());
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Creates and initializes basic properties of a response about an upload.
     */
    private UploadFileResponse createUploadFileResponse(HttpServletRequest request) {
        UploadFileResponse response = new UploadFileResponse();
        response.setRequestType(request.getClass().getName());
        response.setMultipartResolverType(multipartResolver == null ? null : multipartResolver.getClass().getName());
        response.setFileSizeThreshold(maxFileSize);

        return response;
    }
}
