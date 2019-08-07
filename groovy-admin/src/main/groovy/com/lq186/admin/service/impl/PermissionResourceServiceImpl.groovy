package com.lq186.admin.service.impl

import com.lq186.admin.consts.Errors
import com.lq186.admin.context.PageRequestContext
import com.lq186.admin.model.entity.PermissionResource
import com.lq186.admin.exception.CodeException
import com.lq186.admin.repo.PermissionResourceRepo
import com.lq186.admin.service.PermissionResourceService
import com.lq186.admin.util.BeanUtils
import com.lq186.admin.util.RandomUtils
import org.springframework.data.domain.Page
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import javax.transaction.Transactional

@Service
@Transactional
class PermissionResourceServiceImpl implements PermissionResourceService {

    @Resource
    PermissionResourceRepo permissionResourceRepo

    @Override
    Page<PermissionResource> findPage(String queryText, Integer resourceType) {

        permissionResourceRepo.findAll(new Specification<PermissionResource>() {
            @Override
            Predicate toPredicate(Root<PermissionResource> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                def predicates = []
                if (queryText) {
                    def likeText = "$queryText%"
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(root.get("resourceId"), likeText),
                            criteriaBuilder.like(root.get("resourceName"), likeText),
                            criteriaBuilder.like(root.get("resourceValue"), likeText)
                    ))
                }
                if (resourceType != null) {
                    predicates.add(criteriaBuilder.equal(root.get("resourceType"), resourceType))
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
            }
        }, PageRequestContext.getPageRequest())

    }

    @Override
    String save(PermissionResource resource) {
        if (!resource.resourceId || !resource.resourceName
                || !PermissionResource.ResourceType.hasType(resource.resourceType)) {
            return "false"
        }

        def oldResource = permissionResourceRepo.findByResourceId(resource.resourceId)
        if (oldResource) {
            throw new CodeException(Errors.Code.DATA_EXISTS, Errors.Msg.DATA_EXISTS_MSG)
        }

        def parentResourceDataId = resource.parentResourceDataId ?: PermissionResource.ROOT_CATALOG_ID

        Integer maxSerialNumber = permissionResourceRepo.countByParentResourceDataId(parentResourceDataId) + 1
        def serialNumber = resource.serialNumber
        if (!serialNumber || serialNumber > maxSerialNumber) {
            serialNumber = maxSerialNumber
        }
        resource.serialNumber = serialNumber

        if (serialNumber < maxSerialNumber) { // 重排序号，占用位之后全部后移
            permissionResourceRepo.addSerialNumberWhenBigger(serialNumber, parentResourceDataId)
        }

        resource.parentResourceDataId = parentResourceDataId
        resource.dataId = RandomUtils.uuid()
        permissionResourceRepo.save(resource)

        return resource.dataId
    }

    @Override
    Integer maxSerialNumber(String resourceDataId) {
        resourceDataId = resourceDataId ?: PermissionResource.ROOT_CATALOG_ID
        return permissionResourceRepo.countByParentResourceDataId(resourceDataId) + 1
    }

    @Override
    void updateByDataId(PermissionResource resource, String dataId) {
        def oldResource = permissionResourceRepo.findByDataId(resource.resourceId)
        if (!oldResource) {
            throw new CodeException(Errors.Code.DATA_NOT_EXISTS, Errors.Msg.DATA_NOT_EXISTS_MSG)
        }

        if (resource.resourceId) {
            def oldResourceIdResource = permissionResourceRepo.findByResourceId(resource.resourceId)
            if (oldResourceIdResource && oldResourceIdResource.dataId != dataId) {
                throw new CodeException(Errors.Code.DATA_EXISTS, Errors.Msg.DATA_EXISTS_MSG)
            }
        }

        // 原权限资源序号在资源序号之后减1
        permissionResourceRepo.subSerialNumberWhenBigger(oldResource.serialNumber, oldResource.parentResourceDataId)

        BeanUtils.copyPropertiesIfNoneNullAndNotEquals(resource, oldResource,
                ["resourceId", "resourceName", "resourceValue", "resourceType",
                 "parentResourceDataId", "serialNumber"])

        Integer maxSerialNumber = permissionResourceRepo.countByParentResourceDataId(oldResource.parentResourceDataId) + 1
        def newSerialNumber = oldResource.serialNumber
        if (newSerialNumber > maxSerialNumber) {
            newSerialNumber = maxSerialNumber
        }
        oldResource.serialNumber = newSerialNumber

        // 新资源序号在资源序号之后加1
        if (newSerialNumber < maxSerialNumber) {
            permissionResourceRepo.subSerialNumberWhenBigger(oldResource.serialNumber, oldResource.parentResourceDataId)
        }

        permissionResourceRepo.save(resource)
    }

    @Override
    void deleteByDataId(String[] dataIds) {
        if (dataIds) {
            def subData = permissionResourceRepo.findAllByParentResourceDataIdIn(dataIds)
            def subDataIds = subData.collect { it.dataId }
            if (subDataIds) { // 递归删除子资源
                deleteByDataId(subDataIds.toArray(new String[subDataIds.size()]))
            }
            permissionResourceRepo.deleteByDataIdIn(dataIds)
        }
    }

    @Override
    PermissionResource findByDataId(String dataId) {
        return permissionResourceRepo.findByDataId(dataId)
    }
}
