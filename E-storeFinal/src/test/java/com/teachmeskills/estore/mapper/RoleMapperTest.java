package com.teachmeskills.estore.mapper;

import com.teachmeskills.estore.dto.RoleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.ROLE;
import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.ROLE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    void test_convertToRoleDto() {
        RoleDto conrertedRoleDto = roleMapper.convertToRoleDto(ROLE);

        assertEquals(ROLE_DTO, conrertedRoleDto);
    }
}
