package com.kolos.songservice.service.dto;

import lombok.Data;

@Data
public class MetaDataDto {

    private Long resourceId;
    private String resourceName;
    private String album;
    private String artist;
    private Integer year;
    private String length;

}
