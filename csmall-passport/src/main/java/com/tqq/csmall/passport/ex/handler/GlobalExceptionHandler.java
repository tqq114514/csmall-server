package com.tqq.csmall.passport.ex.handler;

import com.tqq.csmall.passport.ex.ServiceException;
import com.tqq.csmall.passport.web.JsonResult;
import com.tqq.csmall.passport.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException exception){
        log.warn("发生了ServiceException异常，异常为：{}",exception.getMessage());

        /*JsonResult jsonResult = new JsonResult();
        jsonResult.setState(1);
        jsonResult.setMessage(exception.getMessage());*/
        /*return new JsonResult().setState(1).setMessage(exception.getMessage());*/
        /*链式方法解决了代码的可读性问题，解决构造方法多参数时，各参数意义不明确的问题*/
        return JsonResult.fail(exception.getServiceCode(), exception.getMessage());
    }

    @ExceptionHandler
    public /*List<String>*/ JsonResult handleBindException(BindException e){
        log.warn("程序运行过程中出现了BindException，将统一处理！");
        log.warn("异常信息：{}", e.getMessage());
        /*几个错误里面随机显示一个*/
        /*JsonResult jsonResult = new JsonResult();
        jsonResult.setState(4);
        jsonResult.setMessage(e.getMessage());
        return jsonResult;*/
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, e.getMessage());

        /*几个错误合并到一起，适合前端弹出信息*/
        /*StringJoiner stringJoiner = new StringJoiner(",","请求参数错误,","!");
        List<FieldError> fieldErrorList =  e.getFieldErrors();
        for (FieldError fieldError:fieldErrorList){
            String defaultMessage = fieldError.getDefaultMessage();
            stringJoiner.add(defaultMessage);
        }
        return stringJoiner.toString();*/

        /*放到集合里面显示，也是显示一个*/
        /*List<String> messageList = new ArrayList<>();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String defaultMessage = fieldError.getDefaultMessage();
            messageList.add(defaultMessage);
        }
        return messageList;*/
    }
    // 如果@ExceptionHandler没有配置参数，则以方法参数的异常为准，来处理异常
    // 如果@ExceptionHandler配置了参数，则只处理此处配置的异常
    @ExceptionHandler({
            InternalAuthenticationServiceException.class,
            BadCredentialsException.class
    })
    public JsonResult handleAuthenticationException(AuthenticationException e){
        log.warn("程序运行过程中出现了AuthenticationException，将统一处理！");
        log.warn("异常：", e);
        String message = "登录失败，用户名或密码错误！";
        return JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED, message);
    }

    /*当删除的id不合法为负数时，处理异常*/
    @ExceptionHandler
    public JsonResult handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("程序运行过程中出现了ConstraintViolationException，将统一处理！");
        log.warn("异常信息：{}", e.getMessage());
        String message = null;
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            message = constraintViolation.getMessage();
        }
       /* JsonResult jsonResult = new JsonResult();
        jsonResult.setState(5);
        jsonResult.setMessage(e.getMessage());
        return jsonResult;*/
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler
    public JsonResult handleDisabledException(DisabledException e) {
        log.warn("程序运行过程中出现了DisabledException，将统一处理！");
        log.warn("异常：", e);
        String message = "登录失败，账号已经被禁用！";
        return JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED_DISABLE, message);
    }

    @ExceptionHandler
    public JsonResult handleThrowable(Throwable throwable){
        log.warn("发生了throwable异常，异常为：{}",throwable.getMessage());
        /*e.printStackTrace();导致线程阻塞，正式项目上线后禁止使用e.printStackTrace()*/
        log.warn("异常：",throwable);
        String message = "服务器忙，请稍后再试【在开发过程中，如果看到此提示，应该检查服务器端的控制台，分析异常，并在全局异常处理器中补充处理对应异常的方法】";

        /*JsonResult jsonResult = new JsonResult();
        jsonResult.setState(99);
        jsonResult.setMessage(throwable.getMessage());
        return jsonResult;*/
        return JsonResult.fail(ServiceCode.ERR_UNKNOWN,throwable.getMessage());
    }
}
