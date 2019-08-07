package com.lq186.admin.model.entity

import com.lq186.admin.common.EntityIdable

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

// 配置信息表
// 可支持缓存
@Entity
@Table(uniqueConstraints = [
        @UniqueConstraint(name = "UK_ITEM_KEY", columnNames = "itemKey"),
        @UniqueConstraint(name = "UK_DATA_ID", columnNames = "dataId")
])
class Setting extends EntityIdable {

    // 配置组名称
    // 支持中文/英文 | 长度40 | 非空 | 默认: Default
    @Column(length = 40, nullable = false)
    String itemGroup

    // 配置项
    // 唯一 | 仅支持英文+数字 | 长度32 | 非空
    @Column(length = 32, nullable = false)
    String itemKey

    // 配置值
    // 支持中文/英文/数字 | 长度64 | 非空
    @Column(length = 64, nullable = false)
    String itemValue

    // 配置描述
    // 支持中文/英文/数字 | 长度250 | 非空 | 默认: 空字符串("")
    @Column(length = 250, nullable = false)
    String itemDescription

    @Override
    protected List<String> jsonProperties() {
        return ["itemGroup", "itemKey", "itemValue", "itemDescription"]
    }
}
