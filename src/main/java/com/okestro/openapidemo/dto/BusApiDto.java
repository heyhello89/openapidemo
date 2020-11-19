package com.okestro.openapidemo.dto;

import lombok.Data;

@Data
public class BusApiDto {

    private String status;
    private String code;
    private String message;

    private BusApiResultDto result;

}
