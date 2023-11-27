package com.codestates.sebmainproject009.survey.service;


import com.codestates.sebmainproject009.survey.Dto.SurveyDto;
import com.codestates.sebmainproject009.user.entity.User;
import com.codestates.sebmainproject009.user.repository.UserRepository;
import com.codestates.sebmainproject009.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SurveyService {
    private final UserRepository userRepository;
    private final UserService userService;

    public SurveyService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public User setUserAllergy(SurveyDto surveyDto, String authorizationHeader) {


        User user = userService.findUserByToken(authorizationHeader);
        user.setWorriedOrgan(User.WorriedOrgan.valueOf(surveyDto.getDisease()));

        String inputAllergy = surveyDto.getAllergy();

        if(Arrays.stream(User.Allergy.values()).anyMatch(i->i.toString().equals(inputAllergy))){
            user.setAllergy(User.Allergy.valueOf(surveyDto.getAllergy()));
            user.setOtherAllergy(null);
        }else {
            user.setAllergy(User.Allergy.OTHER);
            user.setOtherAllergy(inputAllergy);
        }

        return userRepository.save(user);
    }
}
