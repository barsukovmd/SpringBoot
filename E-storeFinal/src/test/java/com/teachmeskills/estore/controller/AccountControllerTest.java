package com.teachmeskills.estore.controller;

import com.teachmeskills.estore.dto.OrderDto;
import com.teachmeskills.estore.security.CustomUserDetail;
import com.teachmeskills.estore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.teachmeskills.estore.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static com.teachmeskills.estore.test_utils.ControllerUtils.getCustomUserDetailRoleAdmin;
import static com.teachmeskills.estore.test_utils.ControllerUtils.getCustomUserDetailRoleUser;
import static com.teachmeskills.estore.utils.Constants.Attributes.USER_ORDER;
import static com.teachmeskills.estore.utils.Constants.ControllerMappingPath.LOGIN;
import static com.teachmeskills.estore.utils.Constants.MappingPath.ACCOUNT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.base-url}")
    private String baseUrl;

    @MockBean
    private OrderService orderService;

    private final String url = "/account";

    @Test
    @WithAnonymousUser
    void test_showAccountPage_anonymous_denied() throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(baseUrl + LOGIN));
    }

    @Test
    void test_showAccountPage_roleUser_allowed() throws Exception {
        CustomUserDetail customUserDetail = getCustomUserDetailRoleUser();
        inspectShowAccountPageByUserRole(customUserDetail);
    }

    @Test
    void test_showAccountPage_roleAdmin_allowed() throws Exception {
        CustomUserDetail customUserDetail = getCustomUserDetailRoleAdmin();
        inspectShowAccountPageByUserRole(customUserDetail);
    }

    private void inspectShowAccountPageByUserRole(CustomUserDetail customUserDetail) throws Exception {
        List<OrderDto> orders = Collections.emptyList();

        when(orderService.getOrdersById(customUserDetail.getUser().getId())).thenReturn(orders);

        mockMvc.perform(get(url)
                        .with(user(customUserDetail)))
                .andExpect(status().isOk())
                .andExpect(model().attribute(USER_ORDER, orders))
                .andExpect(view().name(ACCOUNT));
    }
}
