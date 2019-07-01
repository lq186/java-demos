package com.lq186.admin.service.impl

import com.lq186.admin.config.GlobalSetting
import com.lq186.admin.consts.Errors
import com.lq186.admin.consts.Log
import com.lq186.admin.context.PageRequestContext
import com.lq186.admin.entity.OperationLog
import com.lq186.admin.entity.PermissionResource
import com.lq186.admin.entity.Role
import com.lq186.admin.entity.User
import com.lq186.admin.exception.CodeException
import com.lq186.admin.repo.OperationLogRepo
import com.lq186.admin.repo.PermissionResourceRepo
import com.lq186.admin.repo.RoleRepo
import com.lq186.admin.repo.UserRepo
import com.lq186.admin.service.UserService
import com.lq186.admin.util.BeanUtils
import com.lq186.admin.util.OperationLogUtils
import com.lq186.admin.util.PasswordUtils
import com.lq186.admin.util.RandomUtils
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.transaction.Transactional

@Service
@Transactional
class UserServiceImpl implements UserService {

    @Resource
    UserRepo userRepo

    @Resource
    OperationLogRepo operationLogRepo

    @Resource
    RoleRepo roleRepo

    @Resource
    PermissionResourceRepo permissionResourceRepo

    @Override
    String save(User user, String password) {

        def oldUser = userRepo.findByUsername(user.username)
        if (oldUser) {
            throw new CodeException(Errors.Code.USER_USERNAME_EXISTS, Errors.Msg.USER_USERNAME_EXISTS_MSG)
        }

        def now = System.currentTimeMillis()
        if (!user.activeTime || user.activeTime <= 0) {
            user.activeTime = now
        }

        if (user.invalidTime == null) {
            user.invalidTime = -1
        }

        if (user.useState == null) {
            user.useState = User.UseState.NORMAL
        }

        if (!user.superUser) {
            user.superUser = false
        }

        user.passwordSalt = RandomUtils.randomChars(GlobalSetting.PASSWORD_SALT_LENGTH)
        user.passwordMd5 = PasswordUtils.md5DigestAsHex(password, user.passwordSalt)

        user.dataId = RandomUtils.uuid()
        user.createTime = now

        userRepo.saveAndFlush(user)

        if (GlobalSetting.NORMAL_LOG_ENABLED) {
            String operationResult = "USER:$user.dataId|$user.username|$user.displayName|$user.useState|" +
                    "$user.superUser|$user.activeTime|$user.invalidTime"
            def operationLog = OperationLogUtils.buildNormalOperationLog(OperationLog.OperationType.ADD_DATA,
                    "", operationResult, Log.NORMAL_LOG_PERSIST_MILLIS, ""
            )
            operationLogRepo.saveAndFlush(operationLog)
        }

        return user.dataId
    }

    @Override
    void updateByDataId(User user, String dataId) {
        def oldUser = findByDataIdWithCodeExceptionIfNotExists(dataId)

        def operationData = GlobalSetting.NORMAL_LOG_ENABLED ? "USER:$dataId|$oldUser.displayName|$oldUser.useState|" +
                "$oldUser.superUser|$oldUser.activeTime|$oldUser.invalidTime" : ""

        BeanUtils.copyPropertiesIfNoneNullAndNotEquals(user, oldUser, [
                "displayName",
                "useState",
                "superUser",
                "activeTime",
                "invalidTime"
        ])

        userRepo.updateByDataId(oldUser)

        if (GlobalSetting.NORMAL_LOG_ENABLED) {
            String operationResult = "USER:$dataId|$oldUser.displayName|$oldUser.useState|" +
                    "$oldUser.superUser|$oldUser.activeTime|$oldUser.invalidTime"
            def operationLog = OperationLogUtils.buildNormalOperationLog(OperationLog.OperationType.UPDATE_DATA,
                    operationData, operationResult, Log.NORMAL_LOG_PERSIST_MILLIS, ""
            )
            operationLogRepo.saveAndFlush(operationLog)
        }
    }

    @Override
    Page<User> findPage(String usernameOrDisplayName, Integer useState) {
        if (usernameOrDisplayName) {
            def likeText = usernameOrDisplayName + "%"
            if (User.UseState.ALL.contains(useState)) {
                return userRepo.findAllBy(likeText, likeText, useState, PageRequestContext.getPageRequest())
            } else {
                return userRepo.findAllByUsernameLikeOrDisplayNameLike(likeText, likeText, PageRequestContext.getPageRequest())
            }
        } else {
            if (User.UseState.ALL.contains(useState)) {
                return userRepo.findAllByUseState(useState, PageRequestContext.getPageRequest())
            } else {
                return userRepo.findAll(PageRequestContext.getPageRequest())
            }
        }
    }

    @Override
    void deleteByDataId(String[] dataIds) {

        userRepo.deleteByDataIdIn(dataIds)

        if (GlobalSetting.NORMAL_LOG_ENABLED) {
            String operationData = "USER:$dataIds"
            def operationLog = OperationLogUtils.buildNormalOperationLog(OperationLog.OperationType.DELETE_DATA,
                    operationData, "", Log.NORMAL_LOG_PERSIST_MILLIS, ""
            )
            operationLogRepo.saveAndFlush(operationLog)
        }
    }

    @Override
    void updatePasswordByDataId(String oldPassword, String newPassword, String dataId) {
        def user = findByDataIdWithCodeExceptionIfNotExists(dataId)

        def isMatched = PasswordUtils.isMatched(oldPassword, user.passwordSalt, user.passwordMd5)
        if (!isMatched) {
            throw new CodeException(Errors.Code.USER_PASSWORD_ERROR, Errors.Msg.USER_PASSWORD_ERROR_MSG)
        }

        def newPasswordSalt = RandomUtils.randomChars(GlobalSetting.PASSWORD_SALT_LENGTH)
        def newPasswordMd5 = PasswordUtils.md5DigestAsHex(newPassword, newPasswordSalt)

        userRepo.updatePasswordMd5ByDataId(newPasswordMd5, newPasswordSalt, dataId)

        if (GlobalSetting.NORMAL_LOG_ENABLED) {
            String operationData = "USER:$dataId"
            def operationLog = OperationLogUtils.buildNormalOperationLog(OperationLog.OperationType.MODIFY_PASSWORD,
                    operationData, "", Log.NORMAL_LOG_PERSIST_MILLIS, "修改密码"
            )
            operationLogRepo.saveAndFlush(operationLog)
        }
    }

    @Override
    void restPasswordByDataId(String dataId) {

        findByDataIdWithCodeExceptionIfNotExists(dataId)

        def restPasswordSalt = RandomUtils.randomChars(GlobalSetting.PASSWORD_SALT_LENGTH)
        def restPasswordMd5 = PasswordUtils.md5DigestAsHex(GlobalSetting.DEFAULT_PASSWORD, restPasswordSalt)

        userRepo.updatePasswordMd5ByDataId(restPasswordMd5, restPasswordSalt, dataId)

        if (GlobalSetting.NORMAL_LOG_ENABLED) {
            String operationData = "USER:$dataId"
            def operationLog = OperationLogUtils.buildNormalOperationLog(OperationLog.OperationType.MODIFY_PASSWORD,
                    operationData, "", Log.NORMAL_LOG_PERSIST_MILLIS, "重置密码"
            )
            operationLogRepo.saveAndFlush(operationLog)
        }
    }

    @Override
    User findByDataId(String dataId) {
        return userRepo.findByDataId(dataId)
    }

    @Override
    User findByUsername(String username) {
        return userRepo.findByUsername(username)
    }

    @Override
    List<Role> findAllRole(String dataId) {
        return roleRepo.findAllByUserDataId(dataId)
    }

    @Override
    Page<Role> findRolePage(String dataId) {
        return roleRepo.findPageByUserDataId(dataId, PageRequestContext.getPageRequest())
    }

    @Override
    List<PermissionResource> findAllResources(String dataId) {
        def roleList = findAllRole(dataId)
        if (!roleList) {
            return []
        }
        def roleDataIds = roleList.collect { it.dataId }.toArray(new String[roleList.size()])
        return permissionResourceRepo.findAllByRoleDataIdInFromRel(roleDataIds)
    }

    private def findByDataIdWithCodeExceptionIfNotExists(String dataId) {
        def user = userRepo.findByDataId(dataId)
        if (!user) {
            throw new CodeException(Errors.Code.USER_NOT_EXISTS, Errors.Msg.USER_NOT_EXISTS_MSG)
        }
        return user
    }
}
