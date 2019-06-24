package com.lq186.admin.exception

import com.lq186.admin.common.ResponseBean
import com.lq186.admin.consts.Errors
import com.lq186.admin.consts.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletResponse
import java.lang.reflect.UndeclaredThrowableException

@ControllerAdvice(basePackages = ["com.lq186.admin.controller"])
class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class)

    @ExceptionHandler(CodeException.class)
    @ResponseBody
    def codeException(CodeException codeException, HttpServletResponse response) {
        if (codeException.code == Errors.Code.UNAUTHORIZED) {
            response.status = 401
        } else {
            response.status = 500
        }
        return ResponseBean.failed(codeException.code, codeException.msg, codeException.data)
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    def exception(Exception exception, HttpServletResponse response) {

        if (exception instanceof UndeclaredThrowableException) {
            def ex = (exception as UndeclaredThrowableException).getUndeclaredThrowable()
            if (ex instanceof CodeException) {
                return codeException(ex as CodeException, response)
            }
        }

        logger.error("程序异常。", exception)
        response.status = 500
        return ResponseBean.failed(Response.Code.EXCEPTION, Response.Msg.EXCEPTION_MSG, exception.getMessage())
    }

}
