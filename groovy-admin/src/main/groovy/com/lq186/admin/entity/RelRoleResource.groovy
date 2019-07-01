package com.lq186.admin.entity

import com.lq186.admin.common.EntityIdable

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

// 角色资源关系表
@Entity
@Table(uniqueConstraints = [
        @UniqueConstraint(name = "UK_DATA_ID", columnNames = "dataId")
])
class RelRoleResource extends EntityIdable {

    // 角色数据ID
    String roleDataId

    // 权限资源数据ID
    String resourceDataId

    @Override
    protected List<String> jsonProperties() {
        return ["roleDataId", "resourceDataId"]
    }
}
