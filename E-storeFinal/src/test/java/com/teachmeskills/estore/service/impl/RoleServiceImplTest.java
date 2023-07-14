package com.teachmeskills.estore.service.impl;

import com.teachmeskills.estore.domain.Role;
import com.teachmeskills.estore.dto.RoleDto;
import com.teachmeskills.estore.mapper.RoleMapper;
import com.teachmeskills.estore.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class RoleServiceImplTest {

    @Autowired
    private RoleServiceImpl roleService;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private RoleMapper roleMapper;

    private final String testRole = "testRole";

    @Test
    void test_getRole_isPresent() {
        Role role = Role.builder()
                .role(testRole)
                .build();
        RoleDto roleDto = RoleDto.builder()
                .role(testRole)
                .build();

        when(roleRepository.findRoleByRole(testRole)).thenReturn(Optional.of(role));
        when(roleMapper.convertToRoleDto(role)).thenReturn(roleDto);

        RoleDto foundRole = roleService.getRole(testRole);

        assertEquals(testRole, foundRole.getRole());
        verify(roleRepository, atLeastOnce()).findRoleByRole(testRole);
        verify(roleMapper, atLeastOnce()).convertToRoleDto(role);
    }

    @Test
    void test_getRole_isNotPresent() {
        when(roleRepository.findRoleByRole(testRole)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roleService.getRole(testRole));
        verify(roleRepository, atLeastOnce()).findRoleByRole(testRole);
    }
}
