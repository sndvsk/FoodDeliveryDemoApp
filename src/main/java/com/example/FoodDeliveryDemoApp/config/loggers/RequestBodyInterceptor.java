package com.example.FoodDeliveryDemoApp.config.loggers;

import com.example.FoodDeliveryDemoApp.service.logger.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@ControllerAdvice
public class RequestBodyInterceptor extends RequestBodyAdviceAdapter {

    // from https://github.com/oldfr/Basic-CRUD-API/tree/master/src/main/java/com/example/basiccrudAPIs

    final LoggingService logService;

    final HttpServletRequest request;

    public RequestBodyInterceptor(LoggingService logService, HttpServletRequest request) {
        this.logService = logService;
        this.request = request;
    }


    /**
     * This method is called after the request has been handled by the controller and the response has been created.
     * It logs the request using the displayReq() method of the logging service instance.
     *
     * @param body the Object representing the body of the request
     * @param inputMessage the HttpInputMessage object representing the incoming message
     * @param parameter the MethodParameter object representing the method parameter
     * @param targetType the Type object representing the target type
     * @param converterType the Class object representing the converter type
     * @return the Object representing the body of the request
     */
    @NotNull
    @Override
    public Object afterBodyRead(@NotNull Object body, @NotNull HttpInputMessage inputMessage,
                                @NotNull MethodParameter parameter,
                                @NotNull Type targetType,
                                @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        logService.displayReq(request,body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    /**
     * This method determines if this advice should be applied to the given method parameter,
     * target type and converter type.
     *
     * @param methodParameter the MethodParameter object representing the method parameter
     * @param targetType the Type object representing the target type
     * @param converterType the Class object representing the converter type
     * @return a boolean value indicating whether this advice should be applied to the given method parameter,
     * target type and converter type or not
     */
    @Override
    public boolean supports(@NotNull MethodParameter methodParameter,
                            @NotNull Type targetType,
                            @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

}
