package com.teachmeskills.estore.test_utils;

import com.teachmeskills.estore.domain.Role;
import com.teachmeskills.estore.domain.User;
import com.teachmeskills.estore.security.CustomUserDetail;
import lombok.experimental.UtilityClass;

import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.*;
import static com.teachmeskills.estore.test_utils.Constants.ROLE_ADMIN;
import static com.teachmeskills.estore.test_utils.Constants.ROLE_USER;

@UtilityClass
public class ControllerUtils {

    public static CustomUserDetail getCustomUserDetailRoleUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD);
        user.setRole(Role.builder()
                .role(ROLE_USER)
                .build());
        return new CustomUserDetail(user);
    }

    public static CustomUserDetail getCustomUserDetailRoleAdmin() {
        User user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD);
        user.setRole(Role.builder()
                .role(ROLE_ADMIN)
                .build());
        return new CustomUserDetail(user);
    }
}
