package com.codestates.sebmainproject009.survey.controller;

import com.codestates.sebmainproject009.response.SingleResponseDto;
import com.codestates.sebmainproject009.survey.Dto.SurveyDto;
import com.codestates.sebmainproject009.survey.service.SurveyService;
import com.codestates.sebmainproject009.user.entity.User;
import com.codestates.sebmainproject009.user.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/surveys")
public class SurveyController {


    private final SurveyService surveyService;

    private final UserMapper mapper;

    public SurveyController(SurveyService surveyService, UserMapper mapper){
        this.surveyService = surveyService;
        this.mapper = mapper;
    }
    @PostMapping
    public ResponseEntity postSurvey(@RequestBody SurveyDto surveyDto,
                                     @RequestHeader("Authorization") String authorizationHeader){

        User updateUser = surveyService.setUserAllergy(surveyDto, authorizationHeader);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.userToUserResponseDto(updateUser)), HttpStatus.OK);
    }

}
