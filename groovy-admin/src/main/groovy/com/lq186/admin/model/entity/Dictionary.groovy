package com.lq186.admin.model.entity

import com.lq186.admin.common.EntityIdable

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

// 字典表
@Entity
@Table(uniqueConstraints = [
        @UniqueConstraint(name = "UK_DATA_ID", columnNames = "dataId"),
        @UniqueConstraint(name = "UK_GROUP_VALUE", columnNames = ["dic_group", "dic_value"])
])
class Dictionary extends EntityIdable {

    @Column(name = "dic_value", length = 32, nullable = false)
    String value

    @Column(name = "dic_display", length = 64, nullable = false)
    String display

    @Column(name = "dic_group", length = 32, nullable = false)
    String group

    static final String DEFAULT_GROUP = "DEFAULT"

    @Override
    protected List<String> jsonProperties() {
        return ["value", "display", "group"]
    }
}
