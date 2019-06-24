package com.lq186.admin.common

import com.lq186.admin.consts.Response
import com.lq186.admin.util.BeanUtils
import com.lq186.admin.util.PageUtils
import org.springframework.data.domain.Page

final class ResponseBean implements Serializable {

    String code

    String msg

    Object data

    static ResponseBean success() {
        new ResponseBean(
                code: Response.Code.OK,
                msg: Response.Msg.OK_MSG,
                data: ""
        )
    }

    static ResponseBean success(Page<?> page) {
        new ResponseBean(
                code: Response.Code.OK,
                msg: Response.Msg.OK_MSG,
                data: page ? PageUtils.toMap(page) : ""
        )
    }

    static ResponseBean success(def data) {
        def resultData = toMapIfPossible(data)
        new ResponseBean(
                code: Response.Code.OK,
                msg: Response.Msg.OK_MSG,
                data: resultData ?: ""
        )
    }

    static ResponseBean failed(String code, String msg) {
        new ResponseBean(
                code: code ?: Response.Code.EXCEPTION,
                msg: msg ?: Response.Msg.EXCEPTION_MSG,
                data: ""
        )
    }

    static ResponseBean failed(String code, String msg, def data) {
        def resultData = toMapIfPossible(data)
        new ResponseBean(
                code: code ?: Response.Code.EXCEPTION,
                msg: msg ?: Response.Msg.EXCEPTION_MSG,
                data: resultData ?: ""
        )
    }

    private static Object toMapIfPossible(def data) {
        if (data instanceof Map) {
            return data
        }

        if (data instanceof EntityIdable) {
            return (data as EntityIdable).toMap()
        }

        if (data instanceof List) {
            return (data as List).collect {
                (it instanceof EntityIdable) ? (it as EntityIdable).toMap() : it
            }
        }

        return data
    }
}
