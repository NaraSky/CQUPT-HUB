package com.lb.auth.application.mapper;

import com.lb.auth.application.dto.AuthRolePermissionDTO;
import com.lb.auth.domain.entity.AuthRolePermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthRolePermissionDTOConverter {

    AuthRolePermissionDTOConverter INSTANCE = Mappers.getMapper(AuthRolePermissionDTOConverter.class);

    AuthRolePermissionBO convertDTOToBO(AuthRolePermissionDTO authRolePermissionDTO);

}
