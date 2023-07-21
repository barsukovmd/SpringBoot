package com.tms.estore.service;

import com.tms.estore.dto.ProductDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.tms.estore.utils.Constants.*;
import static com.tms.estore.utils.Constants.Attributes.*;
import static com.tms.estore.utils.Constants.ControllerMappingPath.SEARCH_PARAM_RESULT_PAGINATION;
import static com.tms.estore.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE;
import static com.tms.estore.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static com.tms.estore.utils.Constants.RequestParameters.*;

@Component
@RequiredArgsConstructor
public class SearchFacade {

    private final ProductService productService;

    public ModelAndView getProductsPageBySearchCondition(HttpSession session, String searchCondition) {
        if (!searchCondition.isEmpty()) {
            Set<ProductDto> products = productService.getFoundedProducts(searchCondition);
            session.setAttribute(FOUND_PRODUCTS, products);
        }
        return new ModelAndView(REDIRECT_TO_SEARCH_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE);
    }

    public ModelAndView getSearchFilterResultPagePath(HttpServletRequest request, String category) {
        BigDecimal minPrice = getPrice(request, MIN_PRICE, BigDecimal.ZERO);
        BigDecimal maxPrice = getPrice(request, MAX_PRICE, new BigDecimal(Long.MAX_VALUE));
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        if (session.getAttribute(FOUND_PRODUCTS) != null) {
            session.setAttribute(FILTER_FOUND_PRODUCTS, getProductByFilter(session, category, minPrice, maxPrice));
            modelAndView.setViewName(REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE);
        } else {
            if (!ALL.equals(category)) {
                session.setAttribute(FOUND_PRODUCTS, productService.selectProductsFromCategoryByFilter(category, minPrice, maxPrice));
            } else {
                session.setAttribute(FOUND_PRODUCTS, productService.selectAllProductsByFilter(minPrice, maxPrice));
            }
            modelAndView.setViewName(REDIRECT_TO_SEARCH_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE);
        }
        return modelAndView;
    }

    public void setPagination(HttpSession session, Pageable pageable, ModelAndView modelAndView) {
        Set<ProductDto> foundProducts = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
        Set<ProductDto> filterFoundProducts = (Set<ProductDto>) session.getAttribute(FILTER_FOUND_PRODUCTS);
        if (foundProducts != null || filterFoundProducts != null) {
            List<ProductDto> products = selectSet(foundProducts, filterFoundProducts);
            if (products.size() > 0) {
                int startIndex = pageable.getPageNumber() * pageable.getPageSize();
                int endIndex;
                endIndex = getEndIndex(pageable, products, startIndex);
                Page<ProductDto> page = PageableExecutionUtils.getPage(products.subList(startIndex, endIndex), PageRequest.of(pageable.getPageNumber(), 5), products::size);
                modelAndView.addObject(PAGE, page);
                modelAndView.addObject(URL, SEARCH_PARAM_RESULT_PAGINATION);
            } else {
                Page<ProductDto> page = new PageImpl<>(Collections.emptyList());
                modelAndView.addObject(PAGE, page);
            }
        }
    }

    public void processFilter(HttpSession session, String result, String filter) {
        removeUnsavedAttribute(session, result);
        session.removeAttribute(FILTER);
        setFilterAttribute(session, filter);
    }

    private Set<ProductDto> getProductByFilter(HttpSession session, String type, BigDecimal minPrice, BigDecimal maxPrice) {
        Set<ProductDto> products;
        products = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
        products = applyPriceFilterOnProducts(minPrice, maxPrice, products);
        products = applyTypeFilterOnProducts(type, products);
        return products;
    }

    private List<ProductDto> selectSet(Set<ProductDto> foundProducts, Set<ProductDto> filterFoundProducts) {
        List<ProductDto> products = new ArrayList<>();
        if (filterFoundProducts != null) {
            products = new ArrayList<>(filterFoundProducts);
        } else if (foundProducts != null) {
            products = new ArrayList<>(foundProducts);
        }
        return products;
    }

    private int getEndIndex(Pageable pageable, List<ProductDto> products, int startIndex) {
        int endIndex;
        if (startIndex == 0) {
            endIndex = SEARCH_PAGE_SIZE;
        } else {
            endIndex = startIndex + pageable.getPageSize();
        }
        if (endIndex > products.size()) {
            endIndex = endIndex - (endIndex - products.size());

        }
        return endIndex;
    }

    private BigDecimal getPrice(HttpServletRequest request, String param, BigDecimal defaultValue) {
        String value = request.getParameter(param);
        return StringUtils.isNotBlank(value) ? new BigDecimal(value) : defaultValue;
    }

    private void removeUnsavedAttribute(HttpSession session, String filterFlag) {
        if (!SAVE.equals(filterFlag)) {
            session.removeAttribute(FOUND_PRODUCTS);
            session.removeAttribute(FILTER_FOUND_PRODUCTS);
        }
    }

    private void setFilterAttribute(HttpSession session, String filter) {
        if (TRUE.equals(filter)) {
            session.setAttribute(FILTER, new Object());
        }
    }

    private Set<ProductDto> applyPriceFilterOnProducts(BigDecimal minPrice, BigDecimal maxPrice, Set<ProductDto> products) {
        return products.stream()
                .filter(product -> product.getPrice().compareTo(minPrice) > 0 && product.getPrice().compareTo(maxPrice) < 0)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<ProductDto> applyTypeFilterOnProducts(String type, Set<ProductDto> products) {
        Set<ProductDto> productsByType;
        if (!ALL.equals(type)) {
            productsByType = products.stream()
                    .filter(product -> product.getCategory().equals(type))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else {
            productsByType = products;
        }
        return productsByType;
    }
}
