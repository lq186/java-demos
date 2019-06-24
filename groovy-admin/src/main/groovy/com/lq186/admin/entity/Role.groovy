package com.lq186.admin.entity

import com.lq186.admin.common.EntityIdable

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

// 角色
@Entity
@Table(uniqueConstraints = [
        @UniqueConstraint(name = "UK_DATA_ID", columnNames = "dataId"),
        @UniqueConstraint(name = "UK_ROLE_NAME", columnNames = "roleName")
])
class Role extends EntityIdable {

    // 角色名称
    String roleName

    @Override
    protected List<String> jsonProperties() {
        return ["roleName"]
    }
}
