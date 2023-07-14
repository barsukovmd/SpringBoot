package com.teachmeskills.estore.repository;

import com.teachmeskills.estore.domain.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.Optional;

import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.*;
import static com.teachmeskills.estore.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
@Sql(value = "classpath:sql/user/user-com.teachmeskills.estore.repository-before.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:sql/user/user-com.teachmeskills.estore.repository-after.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final String name = USER_NAME;
    private Optional<User> foundUser;

    @Nested
    class UserByLogin {

        @Test
        void test_findUserByLogin_isPresent() {
            foundUser = userRepository.findUserByLogin(USER_LOGIN);

            assertTrue(foundUser.isPresent());
            assertEquals(name, foundUser.get().getName());
        }

        @Test
        void test_findUserByLogin_isNotPresent() {
            String login = "nonExistUser";

            foundUser = userRepository.findUserByLogin(login);

            assertFalse(foundUser.isPresent());
        }
    }

    @Nested
    class UserById {

        @Test
        void test_findUserById_isPresent() {

            foundUser = userRepository.findUserById(USER_ID);

            assertTrue(foundUser.isPresent());
            assertEquals(name, foundUser.get().getName());
        }

        @Test
        void test_findUserById_isNotPresent() {
            Long id = 0L;

            foundUser = userRepository.findUserById(id);

            assertFalse(foundUser.isPresent());
        }
    }

    @Nested
    class UserByLoginOrEmail {

        private String login = "some";
        private String email = "some";

        @Test
        void test_findUserByLoginOrEmail_isPresentByLogin() {
            login = USER_LOGIN;

            foundUser = userRepository.findUserByLoginOrEmail(login, email);

            assertTrue(foundUser.isPresent());
            assertEquals(name, foundUser.get().getName());
        }

        @Test
        void test_findUserByLoginOrEmail_isPresentByEmail() {
            email = USER_EMAIL;

            foundUser = userRepository.findUserByLoginOrEmail(login, email);

            assertTrue(foundUser.isPresent());
            assertEquals(name, foundUser.get().getName());
        }

        @Test
        void test_findUserByLoginOrEmail_isNotPresent() {
            foundUser = userRepository.findUserByLoginOrEmail(login, email);

            assertFalse(foundUser.isPresent());
        }
    }
}
