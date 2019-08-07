package com.lq186.admin.controller

import com.lq186.admin.common.ResponseBean
import com.lq186.admin.model.entity.Setting
import com.lq186.admin.model.params.AddSettingParam
import com.lq186.admin.model.params.UpdateSettingParam
import com.lq186.admin.model.views.PageData
import com.lq186.admin.model.views.SettingView
import com.lq186.admin.service.SettingService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

@RestController
@Api(value = "/api/settings", description = "配置信息模块")
@RequestMapping(value = "/api/settings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class SettingController extends BaseController<SettingView, Setting, AddSettingParam, UpdateSettingParam> {

    @Resource
    SettingService settingService

    @GetMapping
    @ApiOperation("分页查询配置信息")
    @ApiImplicitParams([
            @ApiImplicitParam(name = "itemText", value = "配置组，配置项，配置值和配置说明 模糊查询"),
            @ApiImplicitParam(name = "page", paramType = "query", value = "页码", defaultValue = "1", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", paramType = "query", value = "页面记录数", defaultValue = "10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sort", paramType = "query", value = "排序方式[prop:direction,...]", required = false, dataTypeClass = String.class)
    ])
    ResponseBean<PageData<SettingView>> query(@RequestParam(name = "itemText", required = false) String itemText) {
        query {
            settingService.findPage(itemText)
        }
    }

    @PostMapping
    @ApiOperation("新增配置信息")
    ResponseBean<String> add(@RequestBody AddSettingParam param) {
        super.add(param)
    }

    @GetMapping("/{id}")
    @ApiOperation("获取配置信息")
    ResponseBean<SettingView> find(@PathVariable("id") String dataId) {
        findEntity(dataId) {
            settingService.findByDataId(dataId)
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("更新配置信息")
    ResponseBean<Void> updateByDataId(@PathVariable("id") String dataId, @RequestBody UpdateSettingParam param) {
        super.update(param, dataId)
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除配置信息")
    ResponseBean<Void> deleteByDataId(@PathVariable("id") String dataId) {
        delete(dataId) {
            settingService.deleteByDataId(dataId)
        }
    }

    @GetMapping("/refresh")
    @ApiOperation("重新加载配置信息")
    ResponseBean<Void> refresh() {
        return ResponseBean.success()
    }

    @Override
    String saveEntity(AddSettingParam param, Setting entity) {
        return settingService.save(entity)
    }

    @Override
    void updateEntity(UpdateSettingParam param, Setting entity, String dataId) {
        settingService.updateByDataId(entity, dataId)
    }

    @Override
    protected List<String> addCheckProperties() {
        return ["itemKey"]
    }

    @Override
    protected List<String> updateCheckProperties() {
        return ["itemKey"]
    }
}
