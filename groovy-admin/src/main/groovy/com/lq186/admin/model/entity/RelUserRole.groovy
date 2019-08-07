package com.lq186.admin.model.entity

import com.lq186.admin.common.EntityIdable

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = [
        @UniqueConstraint(name = "UK_DATA_ID", columnNames = "dataId")
])
class RelUserRole extends EntityIdable {

    @Column(length = 32, nullable = false)
    String userDataId

    @Column(length = 32, nullable = false)
    String roleDataId

    @Override
    protected List<String> jsonProperties() {
        return ["userDataId", "roleDataId"]
    }
}
