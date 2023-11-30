package com.codestates.sebmainproject009.json.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JSONService {

    public JSONObject getJsonBody(ResponseEntity<String> responseEntity){

        String responseBody = responseEntity.getBody();

        JSONObject jsonObject = new JSONObject(responseBody);

        return jsonObject.getJSONObject("body");
    }


    public JSONArray getJsonArray(JSONObject jsonBody) {

        JSONArray jsonArray;

        try{
            jsonArray = jsonBody.getJSONArray("items");
        }catch (JSONException exception){
            return  null;
        }
        return jsonArray;
    }


}
