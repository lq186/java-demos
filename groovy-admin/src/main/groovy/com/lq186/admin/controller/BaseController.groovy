package com.lq186.admin.controller

import com.lq186.admin.common.ResponseBean
import com.lq186.admin.model.params.Param
import com.lq186.admin.util.BeanUtils
import com.lq186.admin.util.PageUtils
import com.lq186.admin.util.RequestParamUtils
import org.springframework.core.ResolvableType
import org.springframework.data.domain.Page

abstract class BaseController<V, E, AddParams, UpdateParams> {

    ResponseBean<Page<V>> query(Closure<Page<E>> closure) {
        Page<E> page = closure.call()
        return ResponseBean.success(PageUtils.toView(page, ResolvableType.forClass(getClass()).getGeneric(0).getRawClass()))
    }

    ResponseBean<String> add(AddParams params) {
        def errorMap = RequestParamUtils.checkNonEmpty(null, params, addCheckProperties())
        addCustomCheck(errorMap, params)
        RequestParamUtils.checkErrorMap(errorMap)
        return ResponseBean.success(saveEntity(params, fromParam(params)))
    }

    E fromParam(Param param) {
        return BeanUtils.entityFromParam(param, ResolvableType.forClass(getClass()).getGeneric(1).getRawClass())
    }

    abstract String saveEntity(AddParams param, E entity)

    ResponseBean<Void> update(UpdateParams params, String dataId) {
        params["id"] = dataId
        def errorMap = RequestParamUtils.checkNonEmpty(null, params, updateCheckProperties())
        updateCustomCheck(errorMap, params)
        RequestParamUtils.checkErrorMap(errorMap)
        updateEntity(params, fromParam(params), dataId)
        return ResponseBean.success()
    }

    abstract void updateEntity(UpdateParams param, E entity, String dataId);

    ResponseBean<Void> delete(String dataId, Closure<Void> closure) {
        RequestParamUtils.checkDataId(dataId)
        closure.call()
        return ResponseBean.success()
    }

    ResponseBean<V> findView(String dataId, Closure<V> closure) {
        RequestParamUtils.checkDataId(dataId)
        V view = closure.call()
        return ResponseBean.success(view)
    }

    ResponseBean<V> findEntity(String dataId, Closure<E> closure) {
        RequestParamUtils.checkDataId(dataId)
        E entity = closure.call()
        return ResponseBean.success(BeanUtils.viewFromEntity(entity, ResolvableType.forClass(getClass()).getGeneric(0).getRawClass()))
    }

    ResponseBean<Void> success(Closure<Void> closure) {
        closure.call()
        return ResponseBean.success()
    }

    protected void addCustomCheck(Map<String, Object> errorMap, AddParams params) {

    }

    protected void updateCustomCheck(Map<String, Object> errorMap, UpdateParams params) {

    }

    protected List<String> addCheckProperties() {
        return []
    }

    protected List<String> updateCheckProperties() {
        return []
    }
}
