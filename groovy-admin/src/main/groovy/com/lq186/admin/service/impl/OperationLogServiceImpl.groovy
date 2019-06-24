package com.lq186.admin.service.impl

import com.lq186.admin.context.PageRequestContext
import com.lq186.admin.entity.OperationLog
import com.lq186.admin.repo.OperationLogRepo
import com.lq186.admin.service.OperationLogService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.transaction.Transactional

@Service
@Transactional
class OperationLogServiceImpl implements OperationLogService {

    @Resource
    OperationLogRepo operationLogRepo

    @Override
    Page<OperationLog> findPageByOperationTypeAndLogTextLike(Integer operationType, String logText) {
        if (operationType == null) {
            if (logText) {
                return operationLogRepo.findAllByLogTextLike("$logText%", PageRequestContext.getPageRequest())
            } else {
                return operationLogRepo.findAll(PageRequestContext.getPageRequest())
            }
        } else {
            if (logText) {
                return operationLogRepo.findAllByOperationTypeAndLogTextLike(operationType, "$logText%", PageRequestContext.getPageRequest())
            } else {
                return operationLogRepo.findAllByOperationType(operationType, PageRequestContext.getPageRequest())
            }
        }
    }

    @Override
    void clearByInvalidTime() {
        operationLogRepo.deleteByInvalidTimeLess(System.currentTimeMillis())
    }

    @Override
    OperationLog findByDataId(String dataId) {
        return operationLogRepo.findByDataId(dataId)
    }
}
