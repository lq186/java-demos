package com.lq186.admin.service.impl

import com.lq186.admin.consts.Errors
import com.lq186.admin.context.PageRequestContext
import com.lq186.admin.model.entity.PermissionResource
import com.lq186.admin.model.entity.RelRoleResource
import com.lq186.admin.model.entity.Role
import com.lq186.admin.exception.CodeException
import com.lq186.admin.repo.PermissionResourceRepo
import com.lq186.admin.repo.RelRoleResourceRepo
import com.lq186.admin.repo.RoleRepo
import com.lq186.admin.service.RoleService
import com.lq186.admin.util.RandomUtils
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.transaction.Transactional

@Service
@Transactional
class RoleServiceImpl implements RoleService {

    @Resource
    RoleRepo roleRepo

    @Resource
    RelRoleResourceRepo relRoleResourceRepo

    @Resource
    PermissionResourceRepo resourceRepo

    @Override
    Page<Role> findPage(String roleName) {
        if (roleName) {
            return roleRepo.findAllByRoleNameLike("$roleName%", PageRequestContext.getPageRequest())
        } else {
            return roleRepo.findAll(PageRequestContext.getPageRequest())
        }
    }

    @Override
    String save(String roleName, String[] resourceIds) {
        if (!roleName) {
            return
        }

        Role oldRole = roleRepo.findByRoleName(roleName)
        if (oldRole) {
            throw new CodeException(Errors.Code.DATA_EXISTS, Errors.Msg.DATA_EXISTS_MSG)
        }

        Role role = new Role(
                dataId: RandomUtils.uuid(),
                roleName: roleName
        )
        roleRepo.save(role)

        clearAndSaveRelRoleResource(false, role, resourceIds)

        return role.dataId
    }

    @Override
    void updateByDataId(String roleName, String dataId) {
        if (!dataId || !roleName) {
            return
        }

        def role = roleRepo.findByDataId(dataId)
        if (!role) {
            throw new CodeException(Errors.Code.DATA_NOT_EXISTS, Errors.Msg.DATA_NOT_EXISTS_MSG)
        }

        Role oldRole = roleRepo.findByRoleName(roleName)
        if (oldRole && oldRole.dataId != dataId) {
            throw new CodeException(Errors.Code.DATA_EXISTS, Errors.Msg.DATA_EXISTS_MSG)
        }

        if (roleName != role.roleName) {
            role.roleName = roleName
            roleRepo.save(role)
        }
    }

    @Override
    void updateRelResourcesByDataId(String[] resourceDataIds, String dataId) {
        if (!dataId) {
            return
        }
        def role = roleRepo.findByDataId(dataId)
        if (!role) {
            throw new CodeException(Errors.Code.DATA_NOT_EXISTS, Errors.Msg.DATA_NOT_EXISTS_MSG)
        }
        clearAndSaveRelRoleResource(true, role, resourceDataIds)
    }

    @Override
    void deleteByDataId(String[] dataIds) {
        if (!dataIds) {
            return
        }

        roleRepo.deleteByDataIdIn(dataIds)

        relRoleResourceRepo.deleteAllByRoleDataIdIn(dataIds)
    }

    @Override
    Page<PermissionResource> findResourcesPageByDataIdIn(String[] dataIds) {
        return resourceRepo.findAllByRoleDataIdInFromRel(dataIds, PageRequestContext.getPageRequest())
    }

    @Override
    List<PermissionResource> findResourcesByDataIdIn(String[] dataIds) {
        return resourceRepo.findAllByRoleDataIdInFromRel(dataIds)
    }

    @Override
    Role findByDataId(String dataId) {
        return roleRepo.findByDataId(dataId)
    }

    private void clearAndSaveRelRoleResource(boolean needClear, Role role, String[] resourceDataIds) {
        if (needClear) {
            relRoleResourceRepo.deleteAllByRoleDataIdIn([role.dataId])
        }
        if (resourceDataIds) {
            def relRoleResourceList = resourceDataIds.collect {
                new RelRoleResource(
                        dataId: RandomUtils.uuid(),
                        roleDataId: role.dataId,
                        resourceDataId: it
                )
            }
            relRoleResourceRepo.saveAll(relRoleResourceList)
        }
    }
}
