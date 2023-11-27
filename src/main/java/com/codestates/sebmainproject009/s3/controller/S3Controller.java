package com.codestates.sebmainproject009.s3.controller;


import com.codestates.sebmainproject009.s3.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping(value = "/postsToS3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postToS3(@RequestParam("image") MultipartFile image,
                                   @RequestHeader("Authorization") String authorizationHeader){

        String imageUrl = s3Service.uploadProfileImg(image, authorizationHeader);

        return new ResponseEntity(imageUrl, HttpStatus.OK);
    }
}
