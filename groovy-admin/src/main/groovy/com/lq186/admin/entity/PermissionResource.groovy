package com.lq186.admin.entity

import com.lq186.admin.common.EntityIdable

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = [
        @UniqueConstraint(name = "UK_DATA_ID", columnNames = "dataId"),
        @UniqueConstraint(name = "UK_RESOURCE_ID", columnNames = "resourceId")
])
// 权限资源信息表
class PermissionResource extends EntityIdable {

    // 资源标识
    String resourceId

    // 资源名称
    String resourceName

    // 资源值
    String resourceValue

    // 资源类型：1 目录 | 2 页面视图 | 3 按钮 | 9 API
    Integer resourceType

    // 上级资源数据ID
    String parentResourceDataId

    // 排序号
    Integer serialNumber

    @Override
    protected List<String> jsonProperties() {
        return ["resourceId", "resourceName", "resourceValue", "resourceType", "parentResourceDataId", "serialNumber"]
    }

    static final String ROOT_CATALOG_ID = "ROOT"

    static class ResourceType {

        static final int CATALOG = 1

        static final int VIEW = 2

        static final int BUTTON = 3

        static final int API = 9

        static final boolean hasType(Integer type) {
            return [CATALOG, VIEW, BUTTON, API].contains(type)
        }
    }

}
