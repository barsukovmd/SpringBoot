package com.teachmeskills.estore.mapper;

import com.teachmeskills.estore.domain.User;
import com.teachmeskills.estore.dto.UserFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CustomMapperConfig.class)
public interface UserMapper {

    @Mapping(source = "roleDto", target = "role")
    User convertToUser(UserFormDto user);

    @Mapping(source = "role", target = "roleDto")
    UserFormDto convertToUserFormDto(User user);
}
