package com.codestates.sebmainproject009.search.service;

import com.codestates.sebmainproject009.api.service.APIServiceImpl;
import com.codestates.sebmainproject009.auth.jwt.JwtTokenizer;
import com.codestates.sebmainproject009.json.service.JSONService;
import com.codestates.sebmainproject009.search.dto.Item;
import com.codestates.sebmainproject009.search.dto.ItemList;
import com.codestates.sebmainproject009.search.mapper.SearchMapper;
import com.codestates.sebmainproject009.user.entity.User;
import com.codestates.sebmainproject009.user.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class SearchService {


    private final JSONService jsonService;

    private final APIServiceImpl apiServiceImpl;

    private final JwtTokenizer jwtTokenizer;

    private final UserService userService;

    private final SearchMapper searchMapper;

    public SearchService(JSONService jsonService, APIServiceImpl apiServiceImpl, JwtTokenizer jwtTokenizer, UserService userService, SearchMapper searchMapper) {
        this.jsonService = jsonService;
        this.apiServiceImpl = apiServiceImpl;
        this.jwtTokenizer = jwtTokenizer;
        this.userService = userService;
        this.searchMapper = searchMapper;
    }

    public List<Item> getInfo(String itemName, String authorizationHeader) {

        // 결과를 담을 리스트 선언
        List<Item> resultList = null;

        try {

            // request url 만들기
            String requestStringURL = apiServiceImpl.getURLWithEasyDrugAPI(itemName, "json", "100");

            URL requestURL = new URL(requestStringURL);

            ResponseEntity<String> responseEntity = apiServiceImpl.getResponse(requestURL);


            // request url 을 통해서 원하는 JSON Object 로 변환시키기
            JSONObject jsonBody = jsonService.getJsonBody(responseEntity);
            JSONArray jsonArray = jsonService.getJsonArray(jsonBody);


            // authorizationHeader 를 통해서 ID 추출하기, 헤더에 토큰이 없거나 토큰이 만료되면 null
            Long userId = jwtTokenizer.getUserId(authorizationHeader);


            // 토큰이 있으면 토큰을 통해 user 를 찾아서 item 리스트 받아오기
            if(userId != null){

                User foundUser = userService.findUser(userId);

                resultList = searchMapper.jsonArrayToItems(jsonArray, foundUser, itemName);

            } else {
                // 토큰이 없다면 user 를 찾을 필요 없어서 찾지 않고 item 리스트 받아오기
                resultList = searchMapper.jsonArrayToItems(jsonArray, itemName);
            }

        }catch (Exception e) {
            return null;
        }

        return resultList;
    }

    public List<ItemList> getInfoList(String itemName, String authorizationHeader){

        // 결과를 담을 리스트 선언
        List<ItemList> resultList = null;

        try {

            // request url 만들기
            String requestStringURL = apiServiceImpl.getURLWithEasyDrugAPI(itemName, "json", "100");

            URL requestURL = new URL(requestStringURL);

            ResponseEntity<String> responseEntity = apiServiceImpl.getResponse(requestURL);


            // request url 을 통해서 원하는 JSON Object 로 변환시키기
            JSONObject jsonBody = jsonService.getJsonBody(responseEntity);
            JSONArray jsonArray = jsonService.getJsonArray(jsonBody);



            // authorizationHeader 를 통해서 ID 추출하기, 헤더에 토큰이 없거나 토큰이 만료되면 null
            Long userId = jwtTokenizer.getUserId(authorizationHeader);


            // 토큰이 있으면 토큰을 통해 claims 를 가져와서 user 를 찾고 itemList 받아오기
            if(userId != null){

                User foundUser = userService.findUser(userId);
                resultList = searchMapper.jsonArrayToItemList(jsonArray, foundUser);

            } else {
                // 토큰이 없다면 user 를 찾을 필요 없어서 찾지 않고 itemList 받아오기
                resultList = searchMapper.jsonArrayToItemList(jsonArray);
            }

        }catch (Exception e){
            return null;
        }


        return resultList;
    }
}
