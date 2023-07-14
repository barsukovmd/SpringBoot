package com.teachmeskills.estore.controller;

import com.teachmeskills.estore.dto.ProductDto;
import com.teachmeskills.estore.model.Location;
import com.teachmeskills.estore.security.CustomUserDetail;
import com.teachmeskills.estore.service.CartService;
import com.teachmeskills.estore.service.ShopFacade;
import com.teachmeskills.estore.utils.Constants;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static com.teachmeskills.estore.utils.Constants.AND_PAGE;
import static com.teachmeskills.estore.utils.Constants.Attributes.FAVORITE_PRODUCTS;
import static com.teachmeskills.estore.utils.Constants.ControllerMappingPath.LOGIN;
import static com.teachmeskills.estore.utils.Constants.MappingPath.*;
import static com.teachmeskills.estore.utils.Constants.RequestParameters.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.base-url}")
    private String baseUrl;

    @MockBean
    private ShopFacade shopFacade;

    @MockBean
    private CartService cartService;

    private final CustomUserDetail customUserDetailRoleAdmin = getCustomUserDetailRoleAdmin();
    private final CustomUserDetail customUserDetailRoleUser = getCustomUserDetailRoleUser();

    @Nested
    class TestShowFavoritesPage {

        private final String url = "/favorites";

        @Test
        @WithAnonymousUser
        void test_showFavoritesPage_anonymous_denied() throws Exception {
            mockMvc.perform(get(url))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_showFavoritesPage_roleUser_allowed() throws Exception {
            inspectshowFavoritesPageByRole(customUserDetailRoleUser);

        }

        @Test
        void test_showFavoritesPage_roleAdmin_allowed() throws Exception {
            inspectshowFavoritesPageByRole(customUserDetailRoleAdmin);
        }

        private void inspectshowFavoritesPageByRole(CustomUserDetail customUserDetail) throws Exception {
            List<ProductDto> products = Collections.emptyList();

            when(shopFacade.getFavoriteProducts(customUserDetail.getUser().getId())).thenReturn(products);

            mockMvc.perform(get(url)
                            .with(user(customUserDetail)))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute(FAVORITE_PRODUCTS, products))
                    .andExpect(view().name(FAVORITES));
        }
    }

    @Nested
    class TestAddProductToFavorite {

        private final String url = "/add-favorite";

        @Test
        @WithAnonymousUser
        void test_addProductToFavorite_anonymous_denied() throws Exception {
            mockMvc.perform(get(url))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_addProductToFavorite_roleUser_allowed_allRequiredParams() throws Exception {
            inspectAddProductToFavoriteByRoleWithAllRequiredParams(customUserDetailRoleUser);
        }

        @Test
        void test_addProductToFavorite_roleAdmin_allowed_allRequiredParams() throws Exception {
            inspectAddProductToFavoriteByRoleWithAllRequiredParams(customUserDetailRoleAdmin);
        }

        private void inspectAddProductToFavoriteByRoleWithAllRequiredParams(CustomUserDetail customUserDetail) throws Exception {
            String path = REDIRECT_TO_SEARCH_RESULT_SAVE + AND_PAGE + PAGE;
            ModelAndView modelAndView = new ModelAndView(path);

            doNothing().when(cartService).addSelectedProduct(customUserDetail.getUser().getId(), PRODUCT_ID, Location.FAVORITE);
            when(shopFacade.getModelAndViewByParams(PRODUCT_ID, FAVORITE, PAGE)).thenReturn(modelAndView);

            mockMvc.perform(get(url)
                            .with(user(customUserDetail))
                            .param(ID, PRODUCT_ID.toString())
                            .param(LOCATION, FAVORITE)
                            .param(Constants.PAGE, PAGE.toString()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name(path));
        }

        @Test
        void test_addProductToFavorite_roleUser_allowed_notAllRequiredParams() throws Exception {
            inspectAddProductToFavoriteByRoleWithNotAllRequiredParams(customUserDetailRoleUser);
        }

        @Test
        void test_addProductToFavorite_roleAdmin_allowed_notAllRequiredParams() throws Exception {
            inspectAddProductToFavoriteByRoleWithNotAllRequiredParams(customUserDetailRoleAdmin);
        }

        private void inspectAddProductToFavoriteByRoleWithNotAllRequiredParams(CustomUserDetail customUserDetail) throws Exception {
            mockMvc.perform(get(url)
                            .with(user(customUserDetail))
                            .param(LOCATION, FAVORITE)
                            .param(Constants.PAGE, PAGE.toString()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name(REDIRECT_TO_SOME_ERROR));
        }
    }

    @Nested
    class TestDeleteProductFromFavorite {

        private final String url = "/delete-favorite";

        @Test
        @WithAnonymousUser
        void test_deleteProductFromFavorite_anonymous_denied() throws Exception {
            mockMvc.perform(get(url))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_addProductToFavorite_roleUser_allowed_allRequiredParams() throws Exception {
            inspectDeleteProductFromFavoriteByRoleWithAllRequiredParams(customUserDetailRoleUser);
        }

        @Test
        void test_addProductToFavorite_roleAdmin_allowed_allRequiredParams() throws Exception {
            inspectDeleteProductFromFavoriteByRoleWithAllRequiredParams(customUserDetailRoleAdmin);
        }

        private void inspectDeleteProductFromFavoriteByRoleWithAllRequiredParams(CustomUserDetail customUserDetail) throws Exception {
            doNothing().when(cartService).deleteProduct(customUserDetail.getUser().getId(), PRODUCT_ID, Location.FAVORITE);

            mockMvc.perform(get(url)
                            .with(user(customUserDetail))
                            .param(ID, PRODUCT_ID.toString()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name(REDIRECT_TO_FAVORITES));
        }

        @Test
        void test_addProductToFavorite_roleUser_allowed_notAllRequiredParams() throws Exception {
            inspectDeleteProductFromFavoriteByRoleWithNotAllRequiredParams(customUserDetailRoleUser);
        }

        @Test
        void test_addProductToFavorite_roleAdmin_allowed_notAllRequiredParamss() throws Exception {
            inspectDeleteProductFromFavoriteByRoleWithNotAllRequiredParams(customUserDetailRoleAdmin);
        }

        private void inspectDeleteProductFromFavoriteByRoleWithNotAllRequiredParams(CustomUserDetail customUserDetail) throws Exception {
            mockMvc.perform(get(url)
                            .with(user(customUserDetail)))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name(REDIRECT_TO_SOME_ERROR));
        }
    }
}
