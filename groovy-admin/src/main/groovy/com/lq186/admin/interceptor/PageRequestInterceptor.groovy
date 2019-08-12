package com.lq186.admin.interceptor

import com.lq186.admin.context.PageRequestContext
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class PageRequestInterceptor implements HandlerInterceptor {

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String page = request.getParameter("page")
        String size = request.getParameter("size")
        // Format: Ex.
        // propA:desc,propB:asc
        String sort = request.getParameter("sort")
        PageRequestContext.setPageRequest(
                page && page.isNumber() && (page as int) > 0 ? (page as int) - 1 : 0,
                size && size.isNumber() && (size as int) > 0 ? (size as int) : 10,
                sort
        )
        return true
    }

    @Override
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        PageRequestContext.clearPageRequest()
    }
}
