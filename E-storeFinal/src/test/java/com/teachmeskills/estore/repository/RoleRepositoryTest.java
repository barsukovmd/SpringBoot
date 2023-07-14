package com.teachmeskills.estore.repository;

import com.teachmeskills.estore.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.Optional;

import static com.teachmeskills.estore.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
@Sql(value = "classpath:sql/role/role-com.teachmeskills.estore.repository-before.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:sql/role/role-com.teachmeskills.estore.repository-after.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private String testRole = "ROLE_USER";

    @Test
    void test_findRoleByRole_isPresent() {
        Optional<Role> roleByRole = roleRepository.findRoleByRole(testRole);

        assertTrue(roleByRole.isPresent());
        assertEquals(testRole, roleByRole.get().getRole());
    }

    @Test
    void test_findRoleByRole_isNotPresent() {
        testRole = "NotExistRole";

        Optional<Role> roleByRole = roleRepository.findRoleByRole(testRole);

        assertFalse(roleByRole.isPresent());
    }
}
