package com.codestates.sebmainproject009.user.controller;


import com.codestates.sebmainproject009.comment.service.CommentService;
import com.codestates.sebmainproject009.commu.entity.Commu;
import com.codestates.sebmainproject009.commu.service.CommuService;
import com.codestates.sebmainproject009.response.SingleResponseDto;
import com.codestates.sebmainproject009.user.dto.UserPatchDto;
import com.codestates.sebmainproject009.user.dto.UserPostDto;
import com.codestates.sebmainproject009.user.dto.UserResponseDto;
import com.codestates.sebmainproject009.user.entity.User;
import com.codestates.sebmainproject009.user.mapper.UserMapper;
import com.codestates.sebmainproject009.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper mapper;

    private final CommuService commuService;

    private final CommentService commentService;

    public UserController(UserService userService, UserMapper mapper, CommuService commuService, CommentService commentService) {
        this.userService = userService;
        this.mapper = mapper;
        this.commuService = commuService;
        this.commentService = commentService;
    }

    @PostMapping("/signup")
    public ResponseEntity postUser(@Valid @RequestBody UserPostDto userPostDto){

        User user = userService.createUser(mapper.userPostDtoToUser(userPostDto));

        URI uri = UriComponentsBuilder.newInstance()
                .path("/users/"+user.getUserId())
                .build().toUri();

        return new ResponseEntity<>(uri, HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity patchUser(@PathVariable @Positive long userId,
                                    @RequestBody UserPatchDto userPatchDto){

        User user = userService.updateUser(mapper.userPatchDtoToUser(userPatchDto));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.userToUserResponseDto(user)),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable @Positive long userId){

        User user = userService.findUser(userId);

        UserResponseDto userResponseDto = mapper.userToUserResponseDto(user);

        return new ResponseEntity<>(new SingleResponseDto<>(userResponseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable @Positive long userId){

        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
