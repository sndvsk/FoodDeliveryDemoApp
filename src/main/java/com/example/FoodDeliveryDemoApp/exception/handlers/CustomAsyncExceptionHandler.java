package com.example.FoodDeliveryDemoApp.exception.handlers;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

@ControllerAdvice
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void handleUncaughtException(@NotNull Throwable ex, Method method, @NotNull Object... params) {
        logger.error("[ASYNC-ERROR] method: {} exception: {}", method.getName(), ex.getMessage());
        //throw new CustomInternalServerError("Error on backend: " + ex.getMessage());
    }

}