package com.teachmeskills.estore.service;

import com.teachmeskills.estore.dto.ProductDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import static com.teachmeskills.estore.test_utils.Constants.TV;
import static com.teachmeskills.estore.utils.Constants.*;
import static com.teachmeskills.estore.utils.Constants.Attributes.*;
import static com.teachmeskills.estore.utils.Constants.ControllerMappingPath.SEARCH_PARAM_RESULT_PAGINATION;
import static com.teachmeskills.estore.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE;
import static com.teachmeskills.estore.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static com.teachmeskills.estore.utils.Constants.RequestParameters.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SearchFacadeTest {

    @Autowired
    private SearchFacade searchFacade;

    @MockBean
    private ProductService productService;

    private final HttpSession session = new MockHttpSession();
    private final HttpServletRequest request = new MockHttpServletRequest();
    private final String path = REDIRECT_TO_SEARCH_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE;
    private final ProductDto productDtoOne = getProductDto(TV, 600L);
    private final ProductDto productDtoTwo = getProductDto("phone", 600L);
    private final ProductDto productDtoThree = getProductDto(TV, 300L);
    private ModelAndView modelAndView = new ModelAndView();
    private Set<ProductDto> foundProducts = Set.of(productDtoOne, productDtoTwo, productDtoThree);
    private Set<ProductDto> filterFoundProducts = Set.of(productDtoOne, productDtoTwo, productDtoThree);

    private ProductDto getProductDto(String category, Long price) {
        return ProductDto.builder()
                .category(category)
                .price(BigDecimal.valueOf(price))
                .build();
    }

    @Nested
    class TestGetProductsPageBySearchCondition {

        private String searchCondition;

        private void doProductsPageBySearchCondition() {
            modelAndView = searchFacade.getProductsPageBySearchCondition(session, searchCondition);
        }

        @Test
        void test_getProductsPageBySearchCondition_notEmptyCondition() {
            searchCondition = "some";
            ProductDto productDto = ProductDto.builder().build();
            Set<ProductDto> products = Set.of(productDto);

            when(productService.getFoundedProducts(searchCondition)).thenReturn(products);

            doProductsPageBySearchCondition();

            assertEquals(products, session.getAttribute(FOUND_PRODUCTS));
            assertEquals(path, modelAndView.getViewName());
        }

        @Test
        void test_getProductsPageBySearchCondition_emptyCondition() {
            searchCondition = "";

            doProductsPageBySearchCondition();

            assertEquals(path, modelAndView.getViewName());
            verify(productService, never()).getFoundedProducts(searchCondition);
        }
    }

    @Nested
    class TestGetSearchFilterResultPagePath {

        private final BigDecimal minPrice = new BigDecimal("500");
        private final BigDecimal maxPrice = new BigDecimal("1000");
        private final Set<ProductDto> products = Set.of(productDtoOne, productDtoTwo, productDtoThree);
        private String category = TV;

        private void doSearchFilterResultPagePath(String category) {
            modelAndView = searchFacade.getSearchFilterResultPagePath(request, category);
        }

        @Test
        void test_getSearchFilterResultPagePath_foundProductsNotEmpty() {
            ((MockHttpServletRequest) request).setParameter(MIN_PRICE, String.valueOf(minPrice));
            ((MockHttpServletRequest) request).setParameter(MAX_PRICE, String.valueOf(maxPrice));
            ((MockHttpServletRequest) request).setSession(session);
            session.setAttribute(FOUND_PRODUCTS, products);

            doSearchFilterResultPagePath(category);

            assertEquals(Set.of(productDtoOne), session.getAttribute(FILTER_FOUND_PRODUCTS));
            assertEquals(REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE, modelAndView.getViewName());
        }

        @Test
        void test_getSearchFilterResultPagePath_foundProductsEmpty_allCategories() {
            ((MockHttpServletRequest) request).setParameter(MIN_PRICE, String.valueOf(minPrice));
            ((MockHttpServletRequest) request).setParameter(MAX_PRICE, String.valueOf(maxPrice));
            ((MockHttpServletRequest) request).setSession(session);

            when(productService.selectProductsFromCategoryByFilter(category, minPrice, maxPrice)).thenReturn(products);

            doSearchFilterResultPagePath(category);

            assertEquals(products, session.getAttribute(FOUND_PRODUCTS));
            assertEquals(path, modelAndView.getViewName());
        }

        @Test
        void test_getSearchFilterResultPagePath_foundProductsEmpty_currentCategory() {
            category = ALL;
            ((MockHttpServletRequest) request).setParameter(MIN_PRICE, String.valueOf(minPrice));
            ((MockHttpServletRequest) request).setParameter(MAX_PRICE, String.valueOf(maxPrice));
            ((MockHttpServletRequest) request).setSession(session);

            when(productService.selectAllProductsByFilter(minPrice, maxPrice)).thenReturn(products);

            doSearchFilterResultPagePath(category);

            assertEquals(products, session.getAttribute(FOUND_PRODUCTS));
            assertEquals(path, modelAndView.getViewName());
        }
    }

    @Nested
    class TestSetPagination {

        private final Pageable pageable = PageRequest.of(0, 3);

        private void doPagination() {
            searchFacade.setPagination(session, pageable, modelAndView);
        }

        private int getSize(ModelAndView modelAndView) {
            return ((Page<ProductDto>) modelAndView.getModel().get(PAGE)).getContent().size();
        }

        @Test
        void test_setPagination_NullSessionAttribute() {
            doPagination();

            assertNull(modelAndView.getModelMap().get(PAGE));
            assertNull(modelAndView.getModelMap().get(URL));
        }

        @Test
        void test_setPagination_NotNullFoundProducts() {
            session.setAttribute(FOUND_PRODUCTS, foundProducts);

            doPagination();

            assertEquals(foundProducts.size(), getSize(modelAndView));
            assertEquals(SEARCH_PARAM_RESULT_PAGINATION, modelAndView.getModelMap().get(URL));
        }

        @Test
        void test_setPagination_NotNullFoundProductsButEmpty() {
            foundProducts = Collections.emptySet();
            session.setAttribute(FOUND_PRODUCTS, foundProducts);

            doPagination();

            assertEquals(foundProducts.size(), getSize(modelAndView));
            assertNull(modelAndView.getModelMap().get(URL));
        }

        @Test
        void test_setPagination_NotNullFilterFoundProducts() {
            session.setAttribute(FILTER_FOUND_PRODUCTS, filterFoundProducts);

            doPagination();

            assertEquals(filterFoundProducts.size(), getSize(modelAndView));
            assertEquals(SEARCH_PARAM_RESULT_PAGINATION, modelAndView.getModelMap().get(URL));
        }

        @Test
        void test_setPagination_NotNullFilterFoundProductsButEmpty() {
            filterFoundProducts = Collections.emptySet();
            session.setAttribute(FILTER_FOUND_PRODUCTS, filterFoundProducts);

            doPagination();

            assertEquals(filterFoundProducts.size(), getSize(modelAndView));
            assertNull(modelAndView.getModelMap().get(URL));
        }

        @Test
        void test_setPagination_NotNullFoundProductsAndFilterFoundProducts() {
            session.setAttribute(FOUND_PRODUCTS, foundProducts);
            filterFoundProducts = Set.of(productDtoOne, productDtoTwo);
            session.setAttribute(FILTER_FOUND_PRODUCTS, filterFoundProducts);

            doPagination();

            assertEquals(filterFoundProducts.size(), getSize(modelAndView));
            assertEquals(SEARCH_PARAM_RESULT_PAGINATION, modelAndView.getModelMap().get(URL));
        }

        @Test
        void test_setPagination_NotNullFoundProductsAndFilterFoundProductsButEmpty() {
            session.setAttribute(FOUND_PRODUCTS, foundProducts);
            filterFoundProducts = Collections.emptySet();
            session.setAttribute(FILTER_FOUND_PRODUCTS, filterFoundProducts);

            doPagination();

            assertEquals(filterFoundProducts.size(), getSize(modelAndView));
            assertNull(modelAndView.getModelMap().get(URL));
        }
    }

    @Nested
    class TestProcessFilter {

        private String filterFlag = SAVE;
        private String filter = "false";

        private void doFilter() {
            searchFacade.processFilter(session, filterFlag, filter);
        }

        @Test
        void test_processFilter_filterFlagSaveTrue() {
            session.setAttribute(FOUND_PRODUCTS, foundProducts);
            session.setAttribute(FILTER_FOUND_PRODUCTS, filterFoundProducts);

            doFilter();

            assertEquals(foundProducts, session.getAttribute(FOUND_PRODUCTS));
            assertEquals(filterFoundProducts, session.getAttribute(FILTER_FOUND_PRODUCTS));
            assertNull(session.getAttribute(FILTER));
        }

        @Test
        void test_processFilter_filterFlagSaveFalse() {
            filterFlag = "delete";
            session.setAttribute(FOUND_PRODUCTS, foundProducts);
            session.setAttribute(FILTER_FOUND_PRODUCTS, filterFoundProducts);

            doFilter();

            assertNull(session.getAttribute(FOUND_PRODUCTS));
            assertNull(session.getAttribute(FILTER_FOUND_PRODUCTS));
            assertNull(session.getAttribute(FILTER));
        }

        @Test
        void test_processFilter_filterTrue() {
            filter = TRUE;
            session.setAttribute(FOUND_PRODUCTS, foundProducts);
            session.setAttribute(FILTER_FOUND_PRODUCTS, filterFoundProducts);

            doFilter();

            assertEquals(foundProducts, session.getAttribute(FOUND_PRODUCTS));
            assertEquals(filterFoundProducts, session.getAttribute(FILTER_FOUND_PRODUCTS));
            assertNotNull(session.getAttribute(FILTER));
        }
    }
}
