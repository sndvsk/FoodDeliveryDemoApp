/*
package com.example.FoodDeliveryDemoApp.config.loggers;

import com.example.FoodDeliveryDemoApp.service.logger.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterceptLog implements HandlerInterceptor {

    // from https://github.com/oldfr/Basic-CRUD-API/tree/master/src/main/java/com/example/basiccrudAPIs

    final LoggingService loggingService;

    public InterceptLog(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    */
/**
     * This method is called before the execution of the controller method to log the request if it's a
     * GET, DELETE, or PUT request.
     *
     * @param request the HttpServletRequest object representing the incoming request
     * @param response the HttpServletResponse object representing the response to the request
     * @param handler the controller method that will be executed.
     * @return true if the request should proceed to the controller method, false if it should be stopped
     *//*

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response,
                             @NotNull Object handler) {
        if(request.getMethod().equals(HttpMethod.GET.name())
                || request.getMethod().equals(HttpMethod.DELETE.name())
                || request.getMethod().equals(HttpMethod.PUT.name()))    {
            loggingService.displayReq(request,null);
        }
        return true;
    }

}
*/
