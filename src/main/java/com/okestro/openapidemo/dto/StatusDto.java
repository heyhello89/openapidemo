package com.okestro.openapidemo.dto;

import lombok.Data;

@Data
public class StatusDto {

    private String status;
    private String code;
    private String message;

    private BusResultDto result;

}
