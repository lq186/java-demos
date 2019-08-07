package com.lq186.admin.model.entity

import com.lq186.admin.common.EntityIdable

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

// 用户信息表
@Entity
@Table(uniqueConstraints = [
        @UniqueConstraint(name = "UK_USERNAME", columnNames = "username"),
        @UniqueConstraint(name = "UK_DATA_ID", columnNames = "dataId")
])
class User extends EntityIdable {

    // 用户名
    @Column(length = 32, nullable = false)
    String username

    // 显示名称
    @Column(length = 64, nullable = false)
    String displayName

    // 密码MD5
    @Column(length = 64, nullable = false)
    String passwordMd5

    // 密码盐值
    @Column(length = 8, nullable = false)
    String passwordSalt

    // 是否超级用户
    // true 是 | false 否
    @Column(nullable = false)
    Boolean superUser

    // 使用状态
    // 1 正常 | 2 锁定 | 3 停用
    @Column(nullable = false)
    Integer useState

    // 有效时间
    // 单位: 毫秒(ms)
    @Column(nullable = false)
    Long activeTime

    // 失效时间
    // 单位: 毫秒(ms)
    @Column(nullable = false)
    Long invalidTime

    // 数据创建时间
    // 单位: 毫秒(ms)
    @Column(nullable = false)
    Long createTime

    @Override
    protected List<String> jsonProperties() {
        return ["username", "displayName", "superUser", "useState", "activeTime", "invalidTime", "createTime"]
    }

    static class UseState {

        def static final ALL = [NORMAL, LOCKED, STOP_USE]

        def static final NORMAL = 1

        def static final LOCKED = 2

        def static final STOP_USE = 3

    }
}
