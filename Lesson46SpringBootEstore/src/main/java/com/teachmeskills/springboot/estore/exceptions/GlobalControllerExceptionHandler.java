package com.teachmeskills.springboot.estore.exceptions;

import com.teachmeskills.springboot.estore.PagesPathEnum;
import com.teachmeskills.springboot.estore.RequestParamsEnum;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    //Put exact ResponseStatus together with Exception
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public ModelAndView handleAuthException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(RequestParamsEnum.ERROR_PARAM.getValue(), ex.getMessage());
        ModelAndView model = new ModelAndView();
        model.setViewName(PagesPathEnum.ERROR_PAGE.getPath());
        model.addAllObjects(modelMap);
        return model;
    }
}
