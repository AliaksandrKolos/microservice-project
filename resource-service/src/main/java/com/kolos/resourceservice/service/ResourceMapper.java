package com.kolos.resourceservice.service;

import com.kolos.resourceservice.data.entity.Resource;
import com.kolos.resourceservice.service.dto.ResourceIdDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    @Mapping(source = "id", target = "resourceId")
    ResourceIdDto toDto(Resource resource);
}
