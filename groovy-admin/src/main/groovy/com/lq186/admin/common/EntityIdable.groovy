package com.lq186.admin.common

import com.lq186.admin.util.BeanUtils

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class EntityIdable implements Serializable {

    // 自增ID
    // 无任何业务意义
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    // 数据ID
    // 实际业务ID
    @Column(length = 32, nullable = false)
    String dataId

    protected abstract List<String> jsonProperties()

    Map<String, Object> toMap() {
        def properties = jsonProperties()
        if (properties == null) {
            properties = []
        }
        properties.add("dataId -> id")
        return BeanUtils.toMap(this, properties)
    }

}
