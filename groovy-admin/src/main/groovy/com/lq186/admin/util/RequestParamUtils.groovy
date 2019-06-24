package com.lq186.admin.util

import com.lq186.admin.consts.Errors
import com.lq186.admin.exception.CodeException

final class RequestParamUtils {

    static Map<String, Object> checkNonEmpty(Map<String, Object> errorMap, Object object, List<String> noneEmptyProps) {
        errorMap = errorMap ?: [:]
        noneEmptyProps.each {
            if (!object[it]) {
                errorMap.put(it, Errors.PARAM_EMPTY_MAP)
            }
        }
        return errorMap
    }

    static Map<String, Object> checkWithClosures(Map<String, Object> errorMap, Object object, Map<String, Closure<Object>> closures) {
        errorMap = errorMap ?: [:]
        closures.each {
            if (!it.value.call(object[it.key])) {
                errorMap.put((it.key), ["code": Errors.Code.PARAM_ERROR, "msg": Errors.Msg.PARAM_ERROR_MSG])
            }
        }
        return errorMap
    }

    static void checkDataId(String dataId) {
        checkParamNoneEmpty("dataId", dataId)
    }

    static void checkParamNoneEmpty(String paramName, def paramValue) {
        if (!paramValue) {
            throw new CodeException(Errors.Code.PARAM_EMPTY, Errors.Msg.PARAM_EMPTY_MSG, [(paramName): Errors.PARAM_EMPTY_MAP])
        }
    }

    static void checkErrorMap(Map<String, Object> errorMap) {
        if (errorMap) {
            throw new CodeException(Errors.Code.PARAM_ERROR, Errors.Msg.PARAM_ERROR_MSG, errorMap)
        }
    }
}
