package com.lq186.admin.common

import com.lq186.admin.consts.Response
import com.lq186.admin.model.views.PageData
import com.lq186.admin.util.PageUtils
import org.springframework.data.domain.Page

final class ResponseBean<T> implements Serializable {

    String code

    String msg

    T data

    static ResponseBean<Void> success() {
        new ResponseBean(
                code: Response.Code.OK,
                msg: Response.Msg.OK_MSG
        )
    }

    static ResponseBean<Map<String, Object>> success(Page<?> page) {
        new ResponseBean(
                code: Response.Code.OK,
                msg: Response.Msg.OK_MSG,
                data: page ? PageUtils.toMap(page) : ""
        )
    }

    static <V, E extends EntityIdable> ResponseBean<PageData<V>> success(Page<E> page, Class<V> classOfV) {
        new ResponseBean<PageData<V>>(
                code: Response.Code.OK,
                msg: Response.Msg.OK_MSG,
                data: page ? PageUtils.toView(page, classOfV) : new PageData<>()
        )
    }

    static <V> ResponseBean<V> success(V view) {
        new ResponseBean(
                code: Response.Code.OK,
                msg: Response.Msg.OK_MSG,
                data: view ?: null
        )
    }

    static ResponseBean<Void> failed(String code, String msg) {
        new ResponseBean(
                code: code ?: Response.Code.EXCEPTION,
                msg: msg ?: Response.Msg.EXCEPTION_MSG
        )
    }

    static <V> ResponseBean<V> failed(String code, String msg, V data) {
        new ResponseBean(
                code: code ?: Response.Code.EXCEPTION,
                msg: msg ?: Response.Msg.EXCEPTION_MSG,
                data: data ?: [:]
        )
    }

}
