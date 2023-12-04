package com.codestates.sebmainproject009.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.codestates.sebmainproject009.exception.BusinessLogicException;
import com.codestates.sebmainproject009.exception.ExceptionCode;
import com.codestates.sebmainproject009.s3.client.CustomS3Client;
import com.codestates.sebmainproject009.s3.utils.GenerateName;
import com.codestates.sebmainproject009.user.entity.User;
import com.codestates.sebmainproject009.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class S3Service {

    private final CustomS3Client customS3Client;

    private final UserService userService;

    private final GenerateName generateName;

    public S3Service(CustomS3Client customS3Client, UserService userService, GenerateName generateName) {
        this.customS3Client = customS3Client;
        this.userService = userService;
        this.generateName = generateName;
    }



    public String uploadProfileImg(MultipartFile image, String authorizationHeader){


        String imageUrl = null;
        if(image!=null){
            imageUrl = uploadToS3(image);
        } else {
            throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
        }


        User user = userService.findUserByToken(authorizationHeader);
        user.setProfileImgUrl(imageUrl);
        userService.updateUser(user);

        return imageUrl;
    }




    public String uploadToS3(MultipartFile image){

        AmazonS3 s3Client = customS3Client.getAmazonS3();
        String bucketName = customS3Client.getBucketName();
        String region = customS3Client.getRegion();

        try {

            // 업로드할 이미지 파일의 이름 생성
            String fileName = null;
            if (image.getOriginalFilename() != null) {
                fileName = generateName.generateFileName(image.getOriginalFilename());
            } else return null;

            // 이미지 파일을 로컬에 저장
            Path tempFile = Files.createTempFile(fileName, "");
            image.transferTo(tempFile.toFile());

            // S3에 이미지 업로드
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, tempFile.toFile());
            s3Client.putObject(putObjectRequest);

            // 업로드된 이미지의 URL 생성
            String imageUrl = "https://s3." + region + ".amazonaws.com/" + bucketName + "/" + fileName;

            return imageUrl;
        } catch (Exception e) {
            // 업로드 실패 처리
            e.printStackTrace();
            return null;
        } finally {
            // S3 클라이언트 종료
            s3Client.shutdown();
        }
    }


}
