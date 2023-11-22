package com.codestates.sebmainproject009.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResponseDto <T> {
    private T data;

    public SingleResponseDto(T data) {
        this.data = data;
    }

}
