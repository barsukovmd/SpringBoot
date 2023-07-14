package com.teachmeskills.estore.controller;

import com.teachmeskills.estore.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.teachmeskills.estore.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static com.teachmeskills.estore.utils.Constants.ControllerMappingPath.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.base-url}")
    private String baseUrl;

    @MockBean
    private CartService cartService;

    private final String url = "/admin";

    @Test
    @WithAnonymousUser
    void test_showAdminPage_anonymous_denied() throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(baseUrl + LOGIN));

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void test_showAdminPage_roleUser_denied() throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ERROR_403));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_showAdminPage_roleAdmin_allowed() throws Exception {
        when(cartService.getMostFavorite()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_INFO));
    }
}
