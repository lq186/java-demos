package com.lq186.admin.service

import com.lq186.admin.entity.PermissionResource
import com.lq186.admin.entity.Role
import com.lq186.admin.entity.User
import org.springframework.data.domain.Page

interface UserService {

    String save(User user, String password)

    /**
     * 用户名或名称模糊查询
     * @param usernameOrDisplayName -用户名或名称, 可空, 空查询所有
     * @param useState -使用状态
     * @return
     */
    Page<User> findPage(String usernameOrDisplayName, Integer useState)

    void updateByDataId(User user, String dataId)

    void deleteByDataId(String[] dataIds)

    void updatePasswordByDataId(String oldPassword, String newPassword, String dataId)

    void restPasswordByDataId(String dataId)

    User findByUsername(String username)

    List<Role> findAllRole(String dataId)

    Page<Role> findRolePage(String dataId)

    List<PermissionResource> findAllResources(String dataId)

    User findByDataId(String dataId)
}