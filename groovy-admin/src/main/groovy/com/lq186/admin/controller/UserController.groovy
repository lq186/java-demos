package com.lq186.admin.controller

import com.lq186.admin.common.ResponseBean
import com.lq186.admin.consts.Parameters
import com.lq186.admin.context.UserIdContenxt
import com.lq186.admin.entity.Role
import com.lq186.admin.entity.User
import com.lq186.admin.exception.CodeException
import com.lq186.admin.consts.Errors
import com.lq186.admin.service.UserService
import com.lq186.admin.util.RequestParamUtils
import org.springframework.http.MediaType
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class UserController extends BaseController<User> {

    @Resource
    UserService userService

    @GetMapping
    def query(@RequestParam(name = "usernameOrDisplayName", required = false) String usernameOrDisplayName,
              @RequestParam(name = "useState", required = false) Integer useState) {
        query {
            userService.findPage(usernameOrDisplayName, useState)
        }
    }

    @PostMapping
    def add(@ModelAttribute User user, @RequestParam(Parameters.PASSWORD) String password,
            @RequestParam(Parameters.CONFIRM_PASSWORD) String confirmPassword) {
        checkUserAndPassword(user, password, confirmPassword)
        add(user) {
            userService.save(user, password)
        }
    }

    @GetMapping("/{dataId}")
    def find(@PathVariable("dataId") String dataId) {
        find(dataId) {
            userService.findByDataId(dataId)
        }
    }

    @PutMapping("/{dataId}")
    def put(@ModelAttribute User user, @PathVariable("dataId") String dataId) {
        update(user, dataId) {
            userService.updateByDataId(user, dataId)
        }
    }

    @DeleteMapping("/{dataId}")
    def delete(@PathVariable("dataId") String dataId) {
        delete(dataId) {
            userService.deleteByDataId(dataId)
        }
    }

    @PutMapping("/modify_password")
    def modifyPassword(@RequestParam(Parameters.OLD_PASSWORD) String oldPassword,
                       @RequestParam(Parameters.NEW_PASSWORD) String newPassword,
                       @RequestParam(Parameters.CONFIRM_PASSWORD) String confirmPassword) {

        checkPassword(oldPassword, newPassword, confirmPassword)

        success {
            String dataId = UserIdContenxt.getUserId()
            userService.updatePasswordByDataId(oldPassword, newPassword, dataId)
        }
    }

    @PutMapping("/{dataId}/rest_password")
    def restPassword(@PathVariable("dataId") String dataId) {
        RequestParamUtils.checkDataId(dataId)

        success {
            userService.restPasswordByDataId(dataId)
        }
    }

    @GetMapping("/{dataId}/roles")
    def roles(@PathVariable("dataId") String dataId,
              @RequestParam(name = "pageable", required = false) Boolean pageable) {
        RequestParamUtils.checkDataId(dataId)

        if (pageable) {
            Page<Role> page = userService.findRolePage(dataId)
            return ResponseBean.success(page)
        } else {
            def roleList = userService.findAllRole(dataId)
            return ResponseBean.success(roleList)
        }
    }

    @GetMapping("/{dataId}/resources")
    def resources(@PathVariable("dataId") String dataId) {
        RequestParamUtils.checkDataId(dataId)
        def resourceList = userService.findAllResources(dataId)
        return ResponseBean.success(resourceList)
    }

    @GetMapping("/check")
    def check(@RequestParam("username") String username) {
        if (!username) {
            throw new CodeException(Errors.Code.PARAM_EMPTY, Errors.Msg.PARAM_EMPTY_MSG,
                    ["username": Errors.PARAM_EMPTY_MAP])
        }

        if (!checkedUsername(username)) {
            throw new CodeException(Errors.Code.PARAM_ERROR, Errors.Msg.PARAM_ERROR_MSG,
                    ["username": [
                            code: Errors.Code.USER_USERNAME_FORMAT_ERROR,
                            msg : Errors.Msg.USER_USERNAME_FORMAT_ERROR_MSG
                    ]])
        }

        User user = userService.findByUsername(username)
        if (user) {
            return ResponseBean.failed(Errors.Code.USER_USERNAME_EXISTS, Errors.Msg.USER_USERNAME_EXISTS_MSG)
        }

        return ResponseBean.success()
    }

    def static checkPassword(String oldPassword, String newPassword, String confirmPassword) {
        def errorMap = [:]

        if (!oldPassword) {
            errorMap.put(Parameters.OLD_PASSWORD, Errors.PARAM_EMPTY_MAP)
        }

        if (!newPassword) {
            errorMap.put(Parameters.NEW_PASSWORD, Errors.PARAM_EMPTY_MAP)
        }

        if (!confirmPassword) {
            errorMap.put(Parameters.CONFIRM_PASSWORD, Errors.PARAM_EMPTY_MAP)
        }

        if (newPassword != confirmPassword) {
            errorMap.put(Parameters.CONFIRM_PASSWORD, [
                    code: Errors.Code.USER_PASSWORD_AND_CONFIRM_MISMATCH,
                    msg : Errors.Msg.USER_PASSWORD_AND_CONFIRM_MISMATCH_MSG
            ])
        }

        if (!checkedPassword(newPassword)) {
            errorMap.put(Parameters.PASSWORD, [
                    code: Errors.Code.USER_PASSWORD_FORMAT_ERROR,
                    msg : Errors.Msg.USER_PASSWORD_FORMAT_ERROR_MSG
            ])
        }

        if (errorMap) {
            throw new CodeException(Errors.Code.PARAM_ERROR, Errors.Msg.PARAM_ERROR_MSG, errorMap)
        }

        return errorMap
    }

    def static checkUserAndPassword(User user, String password, String confirmPassword) {
        def errorMap = [:]
        if (!password) {
            errorMap.put(Parameters.PASSWORD, Errors.PARAM_EMPTY_MAP)
        }

        if (!confirmPassword) {
            errorMap.put(Parameters.CONFIRM_PASSWORD, Errors.PARAM_EMPTY_MAP)
        }

        if (!user.username) {
            errorMap.put("username", Errors.PARAM_EMPTY_MAP)
        } else {
            if (!checkedUsername(user.username)) {
                errorMap.put("username", [
                        code: Errors.Code.USER_USERNAME_FORMAT_ERROR,
                        msg : Errors.Msg.USER_USERNAME_FORMAT_ERROR_MSG
                ])
            }
        }

        if (!user.displayName) {
            errorMap.put("displayName", Errors.PARAM_EMPTY_MAP)
        }

        if (!checkedPassword(password)) {
            errorMap.put(Parameters.PASSWORD, [
                    code: Errors.Code.USER_PASSWORD_FORMAT_ERROR,
                    msg : Errors.Msg.USER_PASSWORD_FORMAT_ERROR_MSG
            ])
        }

        if (password != confirmPassword) {
            errorMap.put(Parameters.CONFIRM_PASSWORD, [
                    code: Errors.Code.USER_PASSWORD_AND_CONFIRM_MISMATCH,
                    msg : Errors.Msg.USER_PASSWORD_AND_CONFIRM_MISMATCH_MSG
            ])
        }

        if (errorMap) {
            throw new CodeException(Errors.Code.PARAM_ERROR, Errors.Msg.PARAM_ERROR_MSG, errorMap)
        }
    }

    def static checkedUsername(String username) {
        if (username.length() < 6) {
            return false
        }

        return true
    }

    def static checkedPassword(String password) {
        if (password.length() < 6) {
            return false
        }

        if (password.isNumber()) {
            return false
        }

        return true
    }

}
