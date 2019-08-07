package com.lq186.admin.controller

import com.lq186.admin.common.ResponseBean
import com.lq186.admin.model.entity.OperationLog
import com.lq186.admin.model.views.OperationLogView
import com.lq186.admin.model.views.PageData
import com.lq186.admin.service.OperationLogService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

@RestController
@Api(value = "/api/operation_logs", description = "操作日志模块")
@RequestMapping(value = "/api/operation_logs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class OperationLogController extends BaseController<OperationLogView, OperationLog, Void, Void> {

    @Resource
    OperationLogService operationLogService

    @GetMapping
    @ApiOperation("分页查询操作日志信息")
    @ApiImplicitParams([
            @ApiImplicitParam(name = "logText", value = "日志内容，模糊搜索"),
            @ApiImplicitParam(name = "operationType", value = "操作类型[1->登录,2->退出,3->修改密码,4->新增数据,5->修改数据,6->删除数据]", allowableValues = "1, 2, 3, 4, 5, 6"),
            @ApiImplicitParam(name = "page", paramType = "query", value = "页码", defaultValue = "1", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", paramType = "query", value = "页面记录数", defaultValue = "10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sort", paramType = "query", value = "排序方式[prop:direction,...]", required = false, dataTypeClass = String.class)
    ])
    ResponseBean<PageData<OperationLogView>> query(@RequestParam(name = "logText", required = false) String logText,
                                                   @RequestParam(name = "operationType", required = false) Integer operationType) {
        query {
            operationLogService.findPageByOperationTypeAndLogTextLike(operationType, logText)
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("获取操作日志信息")
    ResponseBean<OperationLogView> find(@PathVariable("id") String dataId) {
        findEntity(dataId) {
            operationLogService.findByDataId(dataId)
        }
    }

    @PostMapping("/clear")
    ResponseBean<Void> clear() {
        success {
            operationLogService.clearByInvalidTime()
        }
    }

    @Override
    String saveEntity(Void param, OperationLog entity) {
        throw new UnsupportedOperationException()
    }

    @Override
    void updateEntity(Void param, OperationLog entity, String dataId) {
        throw new UnsupportedOperationException()
    }
}
