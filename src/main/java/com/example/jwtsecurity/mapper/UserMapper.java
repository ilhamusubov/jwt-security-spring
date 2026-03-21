package com.example.jwtsecurity.mapper;

import com.example.jwtsecurity.response.RegisterResponseDto;
import com.example.jwtsecurity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    RegisterResponseDto userEntityToRegisterResponseDto(UserEntity userEntity);
}
