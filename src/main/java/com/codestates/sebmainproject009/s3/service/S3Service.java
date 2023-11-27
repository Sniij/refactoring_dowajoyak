package com.codestates.sebmainproject009.s3.service;

import com.codestates.sebmainproject009.exception.BusinessLogicException;
import com.codestates.sebmainproject009.exception.ExceptionCode;
import com.codestates.sebmainproject009.s3.client.CustomS3Client;
import com.codestates.sebmainproject009.user.entity.User;
import com.codestates.sebmainproject009.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {

    private final CustomS3Client customS3Client;

    private final UserService userService;

    public S3Service(CustomS3Client customS3Client, UserService userService) {
        this.customS3Client = customS3Client;
        this.userService = userService;
    }



    public String uploadProfileImg(MultipartFile image, String authorizationHeader){

        String imageUrl = null;
        if(image!=null){
            imageUrl = customS3Client.uploadImageToS3(image);
        } else {
            throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
        }


        User user = userService.findUserByToken(authorizationHeader);
        user.setProfileImgUrl(imageUrl);
        userService.updateUser(user);

        return imageUrl;
    }
}
