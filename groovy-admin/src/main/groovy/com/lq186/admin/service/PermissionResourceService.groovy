package com.lq186.admin.service

import com.lq186.admin.model.entity.PermissionResource
import org.springframework.data.domain.Page

interface PermissionResourceService {

    Page<PermissionResource> findPage(String queryText, Integer resourceType)

    String save(PermissionResource resource)

    void updateByDataId(PermissionResource resource, String dataId)

    void deleteByDataId(String[] dataIds)

    PermissionResource findByDataId(String dataId)

    Integer maxSerialNumber(String resourceDataId)
}