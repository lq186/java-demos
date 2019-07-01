package com.lq186.admin.controller

import com.lq186.admin.entity.OperationLog
import com.lq186.admin.service.OperationLogService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

@RestController
@RequestMapping(value = "/api/operation_logs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class OperationLogController extends BaseController<OperationLog> {

    @Resource
    OperationLogService operationLogService

    @GetMapping
    def query(@RequestParam(name = "logText", required = false) String logText,
              @RequestParam(name = "operationType", required = false) Integer operationType) {
        query {
            operationLogService.findPageByOperationTypeAndLogTextLike(operationType, logText)
        }
    }

    @GetMapping("/{dataId}")
    def find(@PathVariable("dataId") String dataId) {
        find(dataId) {
            operationLogService.findByDataId(dataId)
        }
    }

    @PostMapping("/clear")
    def clear() {
        success {
            operationLogService.clearByInvalidTime()
        }
    }
}
