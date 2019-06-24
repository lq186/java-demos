package com.lq186.admin.controller

import com.lq186.admin.common.ResponseBean
import com.lq186.admin.util.RequestParamUtils
import org.springframework.data.domain.Page

class BaseController<T> {

    ResponseBean query(Closure<Page<?>> closure) {
        return ResponseBean.success(closure.call())
    }

    ResponseBean add(T entity, Closure<String> closure) {
        def errorMap = RequestParamUtils.checkNonEmpty(null, entity, addCheckProperties())
        addCustomCheck(errorMap, entity)
        RequestParamUtils.checkErrorMap(errorMap)
        return ResponseBean.success(closure.call())
    }

    ResponseBean update(T entity, String dataId, Closure<Void> closure) {
        RequestParamUtils.checkDataId(dataId)
        def errorMap = RequestParamUtils.checkNonEmpty(null, entity, updateCheckProperties())
        updateCustomCheck(errorMap, entity)
        RequestParamUtils.checkErrorMap(errorMap)
        closure.call()
        return ResponseBean.success()
    }

    ResponseBean delete(String dataId, Closure<Void> closure) {
        RequestParamUtils.checkDataId(dataId)
        closure.call()
        return ResponseBean.success()
    }

    ResponseBean find(String dataId, Closure<T> closure) {
        RequestParamUtils.checkDataId(dataId)
        T entity = closure.call()
        return ResponseBean.success(entity)
    }

    ResponseBean success(Closure<Void> closure) {
        closure.call()
        return ResponseBean.success()
    }

    protected void addCustomCheck(Map<String, Object> errorMap, T entity) {

    }

    protected void updateCustomCheck(Map<String, Object> errorMap, T entity) {

    }

    protected List<String> addCheckProperties() {
        return []
    }

    protected List<String> updateCheckProperties() {
        return []
    }
}
