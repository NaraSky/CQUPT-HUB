package com.lb.auth.application.converter;

import com.lb.auth.application.dto.AuthPermissionDTO;
import com.lb.auth.domain.entity.AuthPermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthPermissionDTOConverter {

    AuthPermissionDTOConverter INSTANCE = Mappers.getMapper(AuthPermissionDTOConverter.class);

    AuthPermissionBO convertDTOToBO(AuthPermissionDTO authPermissionDTO);

}
