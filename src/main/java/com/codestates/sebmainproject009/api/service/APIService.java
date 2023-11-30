package com.codestates.sebmainproject009.api.service;

import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

public interface APIService {
    String getURLWithEasyDrugAPI(String itemName, String type, String numOfRows) throws UnsupportedEncodingException;

    String getURLWithEasyDrugAPI(String itemName, String type) throws UnsupportedEncodingException;

    ResponseEntity getResponse(URL requestURL) throws URISyntaxException;
}
