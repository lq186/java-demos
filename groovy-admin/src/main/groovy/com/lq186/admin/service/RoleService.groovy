package com.lq186.admin.service

import com.lq186.admin.model.entity.PermissionResource
import com.lq186.admin.model.entity.Role
import org.springframework.data.domain.Page

interface RoleService {

    Page<Role> findPage(String roleName)

    String save(String roleName, String[] resourceIds)

    void updateByDataId(String roleName, String dataId)

    void deleteByDataId(String[] dataIds)

    void updateRelResourcesByDataId(String[] resourceDataIds, String dataId)

    Page<PermissionResource> findResourcesPageByDataIdIn(String[] dataIds)

    List<PermissionResource> findResourcesByDataIdIn(String[] dataIds)

    Role findByDataId(String dataId)
}