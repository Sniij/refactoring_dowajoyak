package com.codestates.sebmainproject009.search.controller;


import com.codestates.sebmainproject009.search.dto.Item;
import com.codestates.sebmainproject009.search.dto.ItemList;
import com.codestates.sebmainproject009.search.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping
    public ResponseEntity getSearchList(@NotNull @RequestParam String itemName,
                                        @Nullable @RequestHeader("Authorization") String authorizationHeader){

        List<ItemList> resultList = null;
        resultList = searchService.getInfoList(itemName, authorizationHeader);

        if(resultList!=null)
            return ResponseEntity.ok().body(resultList);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("찾으시는 데이터가 존재하지 않습니다.");

    }


    @GetMapping("/{itemName}")
    public ResponseEntity getInfo(@NotNull @PathVariable String itemName,
                                  @Nullable @RequestHeader("Authorization") String authorizationHeader){

        List<Item> resultList = null;
        resultList = searchService.getInfo(itemName, authorizationHeader);

        if(resultList!=null)
            return ResponseEntity.ok().body(resultList);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("찾으시는 데이터가 존재하지 않습니다.");

    }


}

