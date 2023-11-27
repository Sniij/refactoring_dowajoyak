package com.codestates.sebmainproject009.comment.controller;

import com.codestates.sebmainproject009.comment.dto.CommentPostDto;
import com.codestates.sebmainproject009.comment.entity.Comment;
import com.codestates.sebmainproject009.comment.mapper.CommentMapper;
import com.codestates.sebmainproject009.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper mapper;

    public CommentController(CommentService commentService, CommentMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @PostMapping("/commu/{commuId}")
    public ResponseEntity postComment(@RequestBody CommentPostDto commentPostDto,
                                      @RequestHeader("Authorization") String authorizationHeader){

        Comment comment = commentService.createComment(commentPostDto, authorizationHeader);

        URI uri = UriComponentsBuilder.newInstance()
                .path("/commu/"+comment.getCommentId()+"/"+comment.getCommentId())
                .build().toUri();

        return new ResponseEntity<>(uri, HttpStatus.CREATED);
    }



}
