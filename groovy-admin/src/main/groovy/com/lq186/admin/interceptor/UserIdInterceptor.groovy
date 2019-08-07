package com.lq186.admin.interceptor

import com.lq186.admin.consts.Errors
import com.lq186.admin.consts.Parameters
import com.lq186.admin.context.UserIdContenxt
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UserIdInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(UserIdInterceptor.class)

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true
        }
        return true
        def token = request.getHeader(Parameters.TOKEN)
        if (!token) {
            token = request.getParameter(Parameters.TOKEN)
        }
        if (!token) {
            logger.error("[token] not found in header or parameter")
            def errorData = '{"code": "' + Errors.Code.UNAUTHORIZED + '", "msg": "' + Errors.Msg.UNAUTHORIZED_MSG + '"}'
            sendJsonData(response, errorData)
            return false
        }

        //TODO: Get user info viewFromEntity redis
        UserIdContenxt.setUserId(token)

        return true
    }

    private void sendJsonData(HttpServletResponse response, String data) {
        PrintWriter printWriter
        try {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            printWriter = response.getWriter()
            printWriter.write(data)
        } finally {
            if (printWriter) {
                printWriter.flush()
                printWriter.close()
            }
        }
    }
}
