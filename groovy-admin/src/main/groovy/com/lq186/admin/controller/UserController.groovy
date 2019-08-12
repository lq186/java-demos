package com.lq186.admin.controller

import com.lq186.admin.annotation.ApiImplicitParamToken
import com.lq186.admin.annotation.ApiPageableParams
import com.lq186.admin.common.ResponseBean
import com.lq186.admin.consts.Parameters
import com.lq186.admin.context.UserIdContenxt
import com.lq186.admin.model.entity.Role
import com.lq186.admin.model.entity.User
import com.lq186.admin.exception.CodeException
import com.lq186.admin.consts.Errors
import com.lq186.admin.model.params.AddUserParam
import com.lq186.admin.model.params.ModifyPasswordParam
import com.lq186.admin.model.params.UpdateUserParam
import com.lq186.admin.model.views.PageData
import com.lq186.admin.model.views.PermissionResourceView
import com.lq186.admin.model.views.RoleView
import com.lq186.admin.model.views.UserView
import com.lq186.admin.service.UserService
import com.lq186.admin.util.RequestParamUtils
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

@RestController
@Api(value = "/api/users", tags = "管理员用户信息模块")
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class UserController extends BaseController<UserView, User, AddUserParam, UpdateUserParam> {

    @Resource
    UserService userService

    @GetMapping
    @ApiOperation("分页查询管理员用户信息")
    @ApiImplicitParams([
            @ApiImplicitParam(name = "token", paramType = "header", value = "access_token", dataTypeClass = String.class),
            @ApiImplicitParam(name = "usernameOrDisplayName", value = "用户名 或 显示名称 模糊查询" ),
            @ApiImplicitParam(name = "useState", value = "使用状态[1 -> 正常, 2 -> 锁定, 3 -> 停用]", allowableValues = "1, 2, 3"),
            @ApiImplicitParam(name = "page", paramType = "query", value = "页码", defaultValue = "1", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", paramType = "query", value = "页面记录数", defaultValue = "10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sort", paramType = "query", value = "排序方式[prop:direction,...]", required = false, dataTypeClass = String.class)
    ])
    ResponseBean<PageData<UserView>> query(@RequestParam(name = "usernameOrDisplayName", required = false) String usernameOrDisplayName,
                                 @RequestParam(name = "useState", required = false) Integer useState) {
        query {
            userService.findPage(usernameOrDisplayName, useState)
        }
    }

    @PostMapping
    @ApiOperation("新增管理员用户信息")
    @ApiImplicitParamToken
    ResponseBean<String> add(@RequestBody AddUserParam param) {
        checkUserAndPassword(param, param.password, param.confirmPassword)
        super.add(param)
    }

    @GetMapping("/{id}")
    @ApiOperation("获取管理员用户信息")
    @ApiImplicitParamToken
    ResponseBean<UserView> find(@PathVariable("id") String dataId) {
        findEntity(dataId) {
            userService.findByDataId(dataId)
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("更新管理员用户信息")
    @ApiImplicitParamToken
    ResponseBean<Void> put(@RequestBody UpdateUserParam param, @PathVariable("id") String dataId) {
        update(param, dataId) {
            userService.updateByDataId(param, dataId)
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除管理员用户信息")
    @ApiImplicitParamToken
    ResponseBean<Void> delete(@PathVariable("id") String dataId) {
        delete(dataId) {
            userService.deleteByDataId(dataId)
        }
    }

    @PutMapping("/modify_password")
    @ApiOperation("修改自己的密码")
    @ApiImplicitParamToken
    ResponseBean<Void> modifyPassword(@RequestBody ModifyPasswordParam param) {

        checkPassword(param.oldPassword, param.newPassword, param.confirmPassword)

        success {
            String dataId = UserIdContenxt.getUserId()
            userService.updatePasswordByDataId(param.oldPassword, param.newPassword, dataId)
        }
    }

    @PutMapping("/{id}/rest_password")
    @ApiOperation("重置管理员用户密码")
    @ApiImplicitParamToken
    ResponseBean<Void> restPassword(@PathVariable("id") String dataId) {
        RequestParamUtils.checkDataId(dataId)

        success {
            userService.restPasswordByDataId(dataId)
        }
    }

    @GetMapping("/{id}/roles/pageable")
    @ApiOperation("分页获取管理员角色信息")
    @ApiPageableParams
    ResponseBean<PageData<RoleView>> rolesPageable(@PathVariable("id") String dataId) {
        RequestParamUtils.checkDataId(dataId)
        Page<Role> page = userService.findRolePage(dataId)
        return ResponseBean.success(page, RoleView.class)
    }

    @GetMapping("/{id}/roles")
    @ApiOperation("获取管理员角色信息")
    @ApiImplicitParamToken
    ResponseBean<List<RoleView>> roles(@PathVariable("id") String dataId) {
        RequestParamUtils.checkDataId(dataId)
        def roleList = userService.findAllRole(dataId)
        return ResponseBean.success(roleList, RoleView.class)
    }

    @GetMapping("/{id}/resources")
    @ApiOperation("获取管理员用户权限资源信息")
    @ApiImplicitParamToken
    ResponseBean<List<PermissionResourceView>> resources(@PathVariable("id") String dataId) {
        RequestParamUtils.checkDataId(dataId)
        def resourceList = userService.findAllResources(dataId)
        return ResponseBean.success(resourceList, PermissionResourceView.class)
    }

    @GetMapping("/check")
    @ApiOperation("检测管理员用户名是否已经被占用")
    @ApiImplicitParamToken
    ResponseBean<Void> check(@RequestParam("username") String username) {
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

    @Override
    String saveEntity(AddUserParam param, User entity) {
        return userService.save(entity, param.password)
    }

    @Override
    void updateEntity(UpdateUserParam param, User entity, String dataId) {
        userService.updateByDataId(entity, dataId)
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
