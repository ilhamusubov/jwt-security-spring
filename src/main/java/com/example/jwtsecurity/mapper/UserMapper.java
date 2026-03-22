package com.example.jwtsecurity.mapper;

import com.example.jwtsecurity.response.RegisterResponseDto;
import com.example.jwtsecurity.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "firstName", source = "firstName")
//    @Mapping(target = "lastName", source = "lastName")
//    @Mapping(target = "email", source = "email")
//    @Mapping(target = "role", source = "role")
    RegisterResponseDto userEntityToRegisterResponseDto(UserEntity userEntity);
}
