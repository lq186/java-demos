package com.lq186.admin.util

import com.lq186.admin.context.UserIdContenxt
import com.lq186.admin.model.entity.OperationLog

final class OperationLogUtils {

    def static buildNormalOperationLog(int operationType, String operationData, String operationResult,
                                       long persistMillis, String operationNotes) {
        OperationLog operationLog = new OperationLog()
        operationLog.userId = UserIdContenxt.getUserId()
        operationLog.operationData = operationData ?: ""
        operationLog.operationType = operationType
        operationLog.operationResult = operationResult ?: ""
        operationLog.operationNotes = operationNotes ?: ""
        operationLog.dataId = RandomUtils.uuid()
        def now = System.currentTimeMillis()
        operationLog.logTime = now
        operationLog.invalidTime = now + persistMillis
        return operationLog
    }

}
