package com.teachmeskills.estore.mapper;

import com.teachmeskills.estore.domain.Role;
import com.teachmeskills.estore.dto.RoleDto;
import org.mapstruct.Mapper;

@Mapper(config = CustomMapperConfig.class)
public interface RoleMapper {

    RoleDto convertToRoleDto(Role role);
}
