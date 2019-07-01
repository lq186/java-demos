package com.lq186.admin.service

import com.lq186.admin.entity.OperationLog
import org.springframework.data.domain.Page

interface OperationLogService {

    Page<OperationLog> findPageByOperationTypeAndLogTextLike(Integer operationType, String logText)

    void clearByInvalidTime()

    OperationLog findByDataId(String dataId)
}