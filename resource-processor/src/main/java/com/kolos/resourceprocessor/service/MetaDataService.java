package com.kolos.resourceprocessor.service;


import com.kolos.resourceprocessor.service.dto.MetaDataDto;

public interface MetaDataService {

    MetaDataDto getMetaData(byte[] data);
}
