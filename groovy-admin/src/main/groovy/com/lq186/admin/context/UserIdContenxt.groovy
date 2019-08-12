package com.lq186.admin.context

import com.lq186.admin.consts.Errors
import com.lq186.admin.exception.CodeException

final class UserIdContenxt {

    private static final ThreadLocal<String> USER_ID_THREAD_LOCAL = new ThreadLocal<>()

    def static setUserId(String userId) {
        USER_ID_THREAD_LOCAL.set(userId)
    }

    def static getUserId() {
        String userId = USER_ID_THREAD_LOCAL.get()
        if (!userId) {
            throw new CodeException(Errors.Code.UNAUTHORIZED, Errors.Msg.UNAUTHORIZED_MSG)
        }
        return userId
    }

    static void clearUserId() {
        USER_ID_THREAD_LOCAL.remove()
    }
}
