package com.blog.exception;

import com.blog.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = {"com.blog.controller","com.blog.service"})
public class UserExceptionControllerAdvice {

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public Result handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题{}，异常类型：{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();

        Map<String,String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError)->{
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return Result.error(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMessage()).put("data",errorMap);


    }



    //自定义异常统一管理
    @ExceptionHandler(value= BusinessException.class)
    public Result handleBusinessException(BusinessException e){
        log.error("错误：",e);
        return Result.error(e.getCode(),e.getMessage());
    }

}

