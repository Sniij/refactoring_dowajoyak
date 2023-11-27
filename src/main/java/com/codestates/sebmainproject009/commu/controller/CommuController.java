package com.codestates.sebmainproject009.commu.controller;

import com.codestates.sebmainproject009.commu.dto.CommuPatchDto;
import com.codestates.sebmainproject009.commu.dto.CommuPostDto;
import com.codestates.sebmainproject009.commu.dto.CommuResponseDto;
import com.codestates.sebmainproject009.commu.dto.CommuResponsesDto;
import com.codestates.sebmainproject009.commu.entity.Commu;
import com.codestates.sebmainproject009.commu.mapper.CommuMapper;
import com.codestates.sebmainproject009.commu.service.CommuService;
import com.codestates.sebmainproject009.response.MultiResponseDto;
import com.codestates.sebmainproject009.response.SingleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/commu")
public class CommuController {
    private final CommuService commuService;
    private final CommuMapper mapper;


    public CommuController(CommuService commuService, CommuMapper mapper) {
        this.commuService = commuService;
        this.mapper = mapper;
    }

    @PostMapping("/posts")
    public ResponseEntity postCommu(@RequestBody CommuPostDto commuPostDto) {

        Commu commu = commuService.createCommu(commuPostDto);

        URI uri = UriComponentsBuilder.newInstance()
                .path("/commu/"+commu.getCommuId())
                .build().toUri();

        return new ResponseEntity<>(uri, HttpStatus.CREATED);

    }


    @GetMapping()
    public ResponseEntity getCommus(@RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "6") int size) {

        Page<Commu> commuPage = commuService.findCommus(page - 1, size);
        List<Commu> commuList = commuPage.getContent();

        MultiResponseDto<List<CommuResponsesDto>> multiResponseDto = new MultiResponseDto<>(mapper.commuListToCommuResponsesDtoList(commuList), commuPage);

        return new ResponseEntity<>(multiResponseDto, HttpStatus.OK);

    }

    @GetMapping("/{commuId}")
    public ResponseEntity getCommu(@PathVariable long commuId){

        Commu commu = commuService.findCommu(commuId);

        CommuResponseDto commuResponseDto = mapper.commuToCommuResponseDto(commu);

        return new ResponseEntity<>(new SingleResponseDto<>(commuResponseDto), HttpStatus.OK);
    }


    @PatchMapping("/{commuId}")
    public ResponseEntity patchCommu(@RequestBody CommuPatchDto commuPatchDto,
                                     @RequestHeader("Authorization") String authorizationHeader){

        Commu updatedCommu = commuService.updateCommu(commuPatchDto, authorizationHeader);


        return new ResponseEntity<>(new SingleResponseDto<>(mapper.commuToCommuResponseDto(updatedCommu)), HttpStatus.OK);
    }


    @DeleteMapping("/{commuId}")
    public ResponseEntity deleteCommu(@PathVariable long commuId,
                                      @RequestHeader("Authorization") String authorizationHeader){


        commuService.deleteCommu(commuId, authorizationHeader);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    /**
     * 이미지와 함께 글을 올릴 때 쓸 API
     * 현재는 사용하지 않고 있음
     * */
    /*
    @PostMapping(value = "/postsToS3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postCommu(@RequestParam("image") @Nullable MultipartFile image,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content,
                                    @RequestParam("userId") Long userId) {

        // 이미지 업로드 및 S3 URL 가져오는 로직
        String imageUrl = null;
        if(image!=null){
            imageUrl = customS3Client.uploadImageToS3(image);
        }

        // CommuPostDto에 이미지 URL 설정
        Commu commu = commuService.createCommuCustom(title, content, imageUrl, userId);


        return new ResponseEntity<>(mapper.commuToCommuResponseDto(commu), HttpStatus.CREATED);
    }

     */

}