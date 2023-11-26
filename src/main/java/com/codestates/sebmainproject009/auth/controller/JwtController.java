package com.codestates.sebmainproject009.auth.controller;


import com.codestates.sebmainproject009.auth.jwt.JwtTokenizer;
import com.codestates.sebmainproject009.user.entity.User;
import com.codestates.sebmainproject009.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
public class JwtController {
    private final JwtTokenizer jwtTokenizer;

    private final UserService userService;

    public JwtController(JwtTokenizer jwtTokenizer, UserService userService) {
        this.jwtTokenizer = jwtTokenizer;
        this.userService = userService;
    }


    @PostMapping("/refresh")
    public ResponseEntity postRefresh(@RequestHeader("refresh") String authorizationHeader){

        User user = userService.findUser(jwtTokenizer.getUserId(authorizationHeader));

        String accessToken = jwtTokenizer.refreshAccessToken(authorizationHeader, user);


        return new ResponseEntity<>(accessToken, HttpStatus.CREATED);
    }
}
