package com.kolos.resourceservice.service;

import com.kolos.resourceservice.service.dto.MetaDataDto;

public interface MetaDataService {

    MetaDataDto getMetaData(byte[] data);
}
