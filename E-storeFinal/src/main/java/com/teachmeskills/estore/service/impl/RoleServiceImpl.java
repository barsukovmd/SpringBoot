package com.teachmeskills.estore.service.impl;

import com.teachmeskills.estore.domain.Role;
import com.teachmeskills.estore.dto.RoleDto;
import com.teachmeskills.estore.mapper.RoleMapper;
import com.teachmeskills.estore.repository.RoleRepository;
import com.teachmeskills.estore.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto getRole(String role) {
        Role userRole = roleRepository.findRoleByRole(role)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return roleMapper.convertToRoleDto(userRole);
    }
}
