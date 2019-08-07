package com.lq186.admin.model.entity

import com.lq186.admin.common.EntityIdable

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

// 操作日志信息表
// 数据需定时清理
@Entity
@Table(uniqueConstraints = [
        @UniqueConstraint(name = "UK_DATA_ID", columnNames = "dataId")
])
class OperationLog extends EntityIdable {

    // 用户ID #{User.dataId}
    @Column(length = 32, nullable = false)
    String userId

    // 操作类型
    // 1 登录 | 2 注销 | 3 修改密码 | 4 新增数据 | 5 修改数据 | 6 删除数据
    @Column(nullable = false)
    Integer operationType

    // 操作数据
    @Column(length = 250, nullable = false)
    String operationData

    // 操作数据结果
    @Column(length = 250, nullable = false)
    String operationResult

    // 操作备注
    @Column(length = 250, nullable = false)
    String operationNotes

    // 日志时间
    // 单位: 毫秒(ms)
    @Column(nullable = false)
    Long logTime

    // 日志失效时间
    // 单位: 毫秒(ms)
    // 超过失效时间的日志将会被清理
    @Column(nullable = false)
    Long invalidTime

    @Override
    protected List<String> jsonProperties() {
        return ["userId", "operationType", "operationData", "operationResult", "operationNotes", "logTime", "invalidTime"]
    }

    static class OperationType {

        static final int LOGIN = 1

        static final int LOGOUT = 2

        static final int MODIFY_PASSWORD = 3

        static final int ADD_DATA = 4

        static final int UPDATE_DATA = 5

        static final int DELETE_DATA = 6

    }
}
