package com.teachmeskills.estore;

import com.teachmeskills.estore.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class EshopSpringBootTestApplicationTest {

    @Autowired
    private AccountController accountController;
    @Autowired
    private CartController cartController;
    @Autowired
    private ExceptionController exceptionController;
    @Autowired
    private FavoriteController favoriteController;
    @Autowired
    private HomeController homeController;
    @Autowired
    private UserController userController;
    @Autowired
    private ProductsController productsController;
    @Autowired
    private SearchController searchController;
    @Autowired
    private AdminController adminController;

    @Test
    void test_accountController_notNull() {
        assertThat(accountController).isNotNull();
    }

    @Test
    void test_cartController_notNull() {
        assertThat(cartController).isNotNull();
    }

    @Test
    void test_exceptionController_notNull() {
        assertThat(exceptionController).isNotNull();
    }

    @Test
    void test_favoriteController_notNull() {
        assertThat(favoriteController).isNotNull();
    }

    @Test
    void test_homeController_notNull() {
        assertThat(homeController).isNotNull();
    }

    @Test
    void test_userController_notNull() {
        assertThat(userController).isNotNull();
    }

    @Test
    void test_productsController_notNull() {
        assertThat(productsController).isNotNull();
    }

    @Test
    void test_searchController_notNull() {
        assertThat(searchController).isNotNull();
    }

    @Test
    void test_adminController_notNull() {
        assertThat(adminController).isNotNull();
    }
}
