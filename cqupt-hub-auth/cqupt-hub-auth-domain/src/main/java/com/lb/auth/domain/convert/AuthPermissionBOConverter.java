package com.lb.auth.domain.convert;

import com.lb.auth.domain.entity.AuthPermissionBO;
import com.lb.auth.infra.basic.entity.AuthPermission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthPermissionBOConverter {

    AuthPermissionBOConverter INSTANCE = Mappers.getMapper(AuthPermissionBOConverter.class);

    AuthPermission convertBOToEntity(AuthPermissionBO authPermissionBO);

}
