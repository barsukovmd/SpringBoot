package com.teachmeskills.estore.validator;

import com.teachmeskills.estore.dto.UserFormDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.*;
import static com.teachmeskills.estore.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
@Sql(value = "classpath:sql/user/user-com.teachmeskills.estore.repository-before.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:sql/user/user-com.teachmeskills.estore.repository-after.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class UserValidatorTest {

    @Autowired
    private UserValidator userValidator;

    private final UserFormDto user = UserFormDto.builder()
            .name(USER_NAME)
            .password(USER_PASSWORD)
            .verifyPassword(USER_PASSWORD)
            .surname(USER_SURNAME)
            .birthday(USER_BIRTHDAY)
            .build();
    private final Errors errors = new BeanPropertyBindingResult(user, "user");
    private String login;
    private String email;

    @Test
    void test_validate_isValid() {
        login = "uniqueLogon";
        email = "uniqueTest@email.com";
        user.setLogin(login);
        user.setEmail(email);

        userValidator.validate(user, errors);

        assertEquals(0, errors.getErrorCount());
    }

    @Test
    void test_validate_isNotValid() {
        login = USER_LOGIN;
        email = USER_EMAIL;
        user.setLogin(login);
        user.setEmail(email);

        userValidator.validate(user, errors);

        assertEquals(2, errors.getErrorCount());
    }
}
