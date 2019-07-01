package com.lq186.admin.consts

final class Errors {

    static class Code {

        static final String USER_PASSWORD_AND_CONFIRM_MISMATCH = "100001"

        static final String USER_PASSWORD_FORMAT_ERROR = "100002"

        static final String USER_USERNAME_EXISTS = "100011"

        static final String USER_USERNAME_FORMAT_ERROR = "100012"

        static final String USER_NOT_EXISTS = "100013"

        static final String USER_PASSWORD_ERROR = "100014"

        static final String USER_USERNAME_OR_PASSWORD_ERROR = "100021"

        static final String USER_STATE_ERROR = "100022"

        static final String USER_ACTIVE_AT = "100023"

        static final String USER_INVALID = "100024"

        static final String SETTING_ITEM_KEY_CAN_NOT_UPDATE = "100031"

        static final String PARAM_EMPTY = "200001"

        static final String PARAM_ERROR = "200002"

        static final String UNAUTHORIZED = "200003"

        static final String DATA_NOT_EXISTS = "300001"

        static final String DATA_EXISTS = "300002"
    }

    static class Msg {

        static final String USER_PASSWORD_AND_CONFIRM_MISMATCH_MSG = "两次输入的密码不匹配"

        static final String USER_PASSWORD_FORMAT_ERROR_MSG = "用户密码格式不正确"

        static final String USER_USERNAME_EXISTS_MSG = "用户名已存在"

        static final String USER_USERNAME_FORMAT_ERROR_MSG = "用户名格式不正确"

        static final String USER_NOT_EXISTS_MSG = "用户不存在"

        static final String USER_PASSWORD_ERROR_MSG = "密码错误"

        static final String USER_USERNAME_OR_PASSWORD_ERROR_MSG = "用户名或密码不正确"

        static final String USER_STATE_ERROR_MSG = "用户状态不正确"

        static final Closure<String> USER_ACTIVE_AT_MSG = { "用户在[$it]之后启用" }

        static final String USER_INVALID_MSG = "用户已失效"

        static final String SETTING_ITEM_KEY_CAN_NOT_UPDATE_MSG = "配置项名称不能更新"

        static final String PARAM_EMPTY_MSG = "参数不能为空"

        static final String PARAM_ERROR_MSG = "参数不正确"

        static final String UNAUTHORIZED_MSG = "未授权的访问"

        static final String DATA_NOT_EXISTS_MSG = "数据不存在"

        static final String DATA_EXISTS_MSG = "数据已存在"
    }

    static final Map<String, Object> PARAM_EMPTY_MAP = [
            code: Errors.Code.PARAM_EMPTY,
            msg : Errors.Msg.PARAM_EMPTY_MSG
    ]
}
