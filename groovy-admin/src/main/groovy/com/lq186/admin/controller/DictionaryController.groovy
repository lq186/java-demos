package com.lq186.admin.controller

import com.lq186.admin.annotation.ApiImplicitParamToken
import com.lq186.admin.common.ResponseBean
import com.lq186.admin.model.entity.Dictionary
import com.lq186.admin.model.params.AddDictionaryParam
import com.lq186.admin.model.params.UpdateDictionaryParam
import com.lq186.admin.model.views.DictionaryView
import com.lq186.admin.model.views.PageData
import com.lq186.admin.service.DictionaryService
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
@Api(value = "/api/dictionaries", tags = "字典信息模块")
@RequestMapping(value = "/api/dictionaries", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class DictionaryController extends BaseController<DictionaryView, Dictionary, AddDictionaryParam, UpdateDictionaryParam> {

    @Resource
    DictionaryService dictionaryService

    @GetMapping
    @ApiOperation(value = "分页查询字典信息")
    @ApiImplicitParams([
            @ApiImplicitParam(name = "token", paramType = "header", value = "access_token", dataTypeClass = String.class),
            @ApiImplicitParam(name = "queryText", value = "检索条件: 显示名称，字典值，分组模糊查询", required = false),
            @ApiImplicitParam(name = "page", paramType = "query", value = "页码", defaultValue = "1", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", paramType = "query", value = "页面记录数", defaultValue = "10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sort", paramType = "query", value = "排序方式[prop:direction,...]", required = false, dataTypeClass = String.class)
    ])
    ResponseBean<PageData<DictionaryView>> query(@RequestParam(name = "queryText", required = false) String queryText) {
        query {
            dictionaryService.findPage(queryText)
        }
    }

    @PostMapping
    @ApiOperation(value = "新增字典信息")
    @ApiImplicitParamToken
    ResponseBean<String> add(@RequestBody AddDictionaryParam param) {
        super.add(param)
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取字典信息")
    @ApiImplicitParamToken
    ResponseBean<DictionaryView> find(@PathVariable("id") String dataId) {
        find(dataId) {
            dictionaryService.findByDataId(dataId)
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改字典信息")
    @ApiImplicitParamToken
    ResponseBean<Void> update(@PathVariable("id") String dataId,
                              @RequestBody UpdateDictionaryParam param) {
        super.update(param, dataId)
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除字典信息")
    @ApiImplicitParamToken
    ResponseBean<Void> delete(@PathVariable("id") String dataId) {
        delete(dataId) {
            dictionaryService.deleteByDataIds(dataId)
        }
    }

    @Override
    String saveEntity(AddDictionaryParam param, Dictionary entity) {
        return dictionaryService.add(entity)
    }

    @Override
    void updateEntity(UpdateDictionaryParam param, Dictionary entity, String dataId) {
        dictionaryService.updateByDataId(entity, dataId)
    }

    @Override
    protected List<String> addCheckProperties() {
        return ["value", "display"]
    }

    @Override
    protected List<String> updateCheckProperties() {
        return ["value", "display"]
    }
}
