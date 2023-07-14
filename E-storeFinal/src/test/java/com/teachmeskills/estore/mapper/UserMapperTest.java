package com.teachmeskills.estore.mapper;

import com.teachmeskills.estore.domain.User;
import com.teachmeskills.estore.dto.UserFormDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private final UserFormDto userFormDto = UserFormDto.builder()
            .id(USER_ID)
            .login(USER_LOGIN)
            .password(USER_PASSWORD)
            .name(USER_NAME)
            .surname(USER_SURNAME)
            .email(USER_EMAIL)
            .birthday(USER_BIRTHDAY)
            .roleDto(ROLE_DTO)
            .build();

    @Test
    void test_convertToUser() {
        String verifyPassword = USER_PASSWORD;
        userFormDto.setVerifyPassword(verifyPassword);

        User convertUser = userMapper.convertToUser(userFormDto);

        assertEquals(USER, convertUser);
    }

    @Test
    void test_convertToUserFormDto() {
        UserFormDto convertUser = userMapper.convertToUserFormDto(USER);

        assertEquals(userFormDto, convertUser);
    }
}
