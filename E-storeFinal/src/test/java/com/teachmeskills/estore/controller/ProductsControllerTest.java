package com.teachmeskills.estore.controller;

import com.teachmeskills.estore.service.ProductService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import static com.teachmeskills.estore.test_utils.Constants.*;
import static com.teachmeskills.estore.utils.Constants.CATEGORY;
import static com.teachmeskills.estore.utils.Constants.ControllerMappingPath.ESHOP;
import static com.teachmeskills.estore.utils.Constants.MappingPath.PRODUCT;
import static com.teachmeskills.estore.utils.Constants.MappingPath.REDIRECT_TO_ESHOP;
import static com.teachmeskills.estore.utils.Constants.PAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ModelAndView expectedModelAndView;
    private Long productId;

    @Nested
    class TestShowProductsPage {

        @Test
        @WithAnonymousUser
        void test_showProductsPage_anonymousUser() throws Exception {
            inspectShowProductsPage();
        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_showProductsPage_userWithRole() throws Exception {
            inspectShowProductsPage();
        }

        private void inspectShowProductsPage() throws Exception {
            String category = TV;
            expectedModelAndView = new ModelAndView(REDIRECT_TO_ESHOP);

            when(productService.getViewProductsByCategory(eq(category), any(Pageable.class))).thenReturn(expectedModelAndView);

            mockMvc.perform(get("/products-page")
                            .param(CATEGORY, category)
                            .param(PAGE, "0")
                            .param(SIZE, "5"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(ESHOP));
        }
    }

    @Nested
    class TestShowProductPage {

        private final String url = "/product/{id}";

        @Test
        @WithAnonymousUser
        void test_showProductPage_anonymousUser_withPathVariable() throws Exception {
            inspectShowProductPageWithPathVariable();
        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_showProductPage_userWithRole_withPathVariable() throws Exception {
            inspectShowProductPageWithPathVariable();
        }

        private void inspectShowProductPageWithPathVariable() throws Exception {
            productId = PRODUCT_ID;
            expectedModelAndView = new ModelAndView(PRODUCT);

            when(productService.getViewProduct(productId)).thenReturn(expectedModelAndView);

            mockMvc.perform(get(url, productId))
                    .andExpect(status().isOk())
                    .andExpect(view().name(PRODUCT));
        }

        @Test
        @WithAnonymousUser
        void test_showProductPage_anonymousUser_withoutPathVariable() throws Exception {
            inspectShowProductPageWithoutPathVariable();
        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_showProductPage_userWithRole_withoutPathVariable() throws Exception {
            inspectShowProductPageWithoutPathVariable();
        }

        private void inspectShowProductPageWithoutPathVariable() throws Exception {
            mockMvc.perform(get(url, productId))
                    .andExpect(status().is4xxClientError());
        }
    }
}
