package com.lq186.admin.exception

class CodeException extends Exception {

    String code

    String msg

    Object data

    CodeException(String code, String msg) {
        super("code: $code, consts: $msg")

        this.code = code
        this.msg = msg
    }

    CodeException(String code, String msg, Object data) {
        this(code, msg)
        this.data = data
    }

}
