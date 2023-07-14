package com.teachmeskills.estore.utils;

import com.teachmeskills.estore.domain.User;
import com.teachmeskills.estore.dto.CartDto;
import com.teachmeskills.estore.security.CustomUserDetail;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.List;

import static com.teachmeskills.estore.utils.Constants.AND_PAGE;

@UtilityClass
public class ControllerUtils {

    public static BigDecimal getProductsPrice(List<CartDto> carts) {
        BigDecimal fullPrice = BigDecimal.ZERO;
        for (CartDto cart : carts) {
            BigDecimal totalPrice = cart.getProductDto().getPrice().multiply(new BigDecimal(cart.getCount()));
            fullPrice = fullPrice.add(totalPrice);
        }
        return fullPrice;
    }

    public static User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail principal = (CustomUserDetail) authentication.getPrincipal();
        return principal.getUser();
    }

    public static Long getAuthenticationUserId() {
        return getAuthenticationUser().getId();
    }

    public static String getPathByPagination(Integer page, String path) {
        if (page == null) {
            return path;
        } else {
            return path + AND_PAGE + page;
        }
    }
}
