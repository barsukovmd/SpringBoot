package com.teachmeskills.estore.controller;

import com.teachmeskills.estore.security.CustomUserDetail;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.teachmeskills.estore.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static com.teachmeskills.estore.test_utils.ControllerUtils.getCustomUserDetailRoleAdmin;
import static com.teachmeskills.estore.test_utils.ControllerUtils.getCustomUserDetailRoleUser;
import static com.teachmeskills.estore.utils.Constants.ControllerMappingPath.LOGIN;
import static com.teachmeskills.estore.utils.Constants.MappingPath.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.base-url}")
    private String baseUrl;

    private final CustomUserDetail customUserDetailRoleAdmin = getCustomUserDetailRoleAdmin();
    private final CustomUserDetail customUserDetailRoleUser = getCustomUserDetailRoleUser();

    @Nested
    class TestShowError500Page {

        private final String url = "/error-500";

        @Test
        @WithAnonymousUser
        void test_showError500Page_anonymous_denied() throws Exception {
            mockMvc.perform(get(url))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_showError500Page_roleUser_allowed() throws Exception {
            mockMvc.perform(get(url)
                            .with(user(customUserDetailRoleUser)))
                    .andExpect(status().isOk())
                    .andExpect(view().name(ERROR_500));
        }

        @Test
        void test_showError500Page_roleAdmin_allowed() throws Exception {
            mockMvc.perform(get(url)
                            .with(user(customUserDetailRoleAdmin)))
                    .andExpect(status().isOk())
                    .andExpect(view().name(ERROR_500));
        }
    }

    @Nested
    class TestShowSomeErrorPage {

        private final String url = "/some-error";

        @Test
        @WithAnonymousUser
        void test_showSomeErrorPage_anonymous_denied() throws Exception {
            mockMvc.perform(get(url))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_showSomeErrorPage_roleUser_allowed() throws Exception {
            mockMvc.perform(get(url)
                            .with(user(customUserDetailRoleUser)))
                    .andExpect(status().isOk())
                    .andExpect(view().name(SOME_ERROR));
        }

        @Test
        void test_showSomeErrorPage_roleAdmin_allowed() throws Exception {
            mockMvc.perform(get(url)
                            .with(user(customUserDetailRoleAdmin)))
                    .andExpect(status().isOk())
                    .andExpect(view().name(SOME_ERROR));
        }
    }

    @Nested
    class TestShowError403Page {

        private final String url = "/error-403";

        @Test
        void test_showError403Page_anonymous_denied() throws Exception {
            mockMvc.perform(get(url))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_showError403Page_roleUser_allowed() throws Exception {
            mockMvc.perform(get(url)
                            .with(user(customUserDetailRoleUser)))
                    .andExpect(status().isOk())
                    .andExpect(view().name(ERROR_403));
        }

        @Test
        void test_showError403Page_roleAdmin_allowed() throws Exception {
            mockMvc.perform(get(url)
                            .with(user(customUserDetailRoleAdmin)))
                    .andExpect(status().isOk())
                    .andExpect(view().name(ERROR_403));
        }
    }
}
