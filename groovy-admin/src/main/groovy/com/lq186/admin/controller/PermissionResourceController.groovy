package com.lq186.admin.controller

import com.lq186.admin.common.ResponseBean
import com.lq186.admin.model.entity.PermissionResource
import com.lq186.admin.model.params.AddPermissionResourceParam
import com.lq186.admin.model.params.Param
import com.lq186.admin.model.params.UpdateDictionaryParam
import com.lq186.admin.model.params.UpdatePermissionResourceParam
import com.lq186.admin.model.views.PageData
import com.lq186.admin.model.views.PermissionResourceView
import com.lq186.admin.service.PermissionResourceService
import com.lq186.admin.util.RequestParamUtils
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

@RestController
@Api(value = "/api/resources", description = "权限资源信息模块")
@RequestMapping(value = "/api/resources", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class PermissionResourceController extends BaseController<PermissionResourceView, PermissionResource, AddPermissionResourceParam, UpdatePermissionResourceParam> {

    @Resource
    PermissionResourceService permissionResourceService

    @GetMapping
    @ApiOperation("分页查询权限资源信息")
    @ApiImplicitParams([
            @ApiImplicitParam(name = "queryText", value = "资源编码，资源名称，资源值 模糊查询"),
            @ApiImplicitParam(name = "resourceType", value = "资源类型[1 -> 目录, 2 -> 页面, 3 -> 按钮, 9 -> API]", allowableValues = "1, 2, 3, 9"),
            @ApiImplicitParam(name = "page", paramType = "query", value = "页码", defaultValue = "1", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", paramType = "query", value = "页面记录数", defaultValue = "10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sort", paramType = "query", value = "排序方式[prop:direction,...]", required = false, dataTypeClass = String.class)

    ])
    ResponseBean<PageData<PermissionResourceView>> query(@RequestParam(name = "queryText", required = false) String queryText,
                                                         @RequestParam(name = "resourceType", required = false) Integer resourceType) {
        query {
            permissionResourceService.findPage(queryText, resourceType)
        }
    }

    @PostMapping
    @ApiOperation("新增权限资源信息")
    ResponseBean<String> add(@RequestBody AddPermissionResourceParam param) {
        super.add(param)
    }

    @GetMapping("/{id}")
    @ApiOperation("获取权限资源信息")
    def find(@PathVariable("id") String dataId) {
        findEntity(dataId) {
            permissionResourceService.findByDataId(dataId)
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("更新权限资源信息")
    def update(@PathVariable("id") String dataId, @RequestBody UpdatePermissionResourceParam param) {
        super.update(param, dataId)
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除权限资源信息")
    def delete(@PathVariable("id") String dataId) {
        delete(dataId) {
            permissionResourceService.deleteByDataId(dataId)
        }
    }

    @GetMapping("/max_serial_number")
    @ApiOperation("获取权限资源下的最大序号, 默认根权限资源下的最大序号")
    def maxSerialNumber(@RequestParam(name = "id", required = false, defaultValue = PermissionResource.ROOT_CATALOG_ID) String dataId) {
        def maxSerialNumber = permissionResourceService.maxSerialNumber(dataId)
        return ResponseBean.success(maxSerialNumber)
    }

    @Override
    String saveEntity(AddPermissionResourceParam param, PermissionResource entity) {
        return permissionResourceService.save(entity)
    }

    @Override
    void updateEntity(UpdatePermissionResourceParam param, PermissionResource entity, String dataId) {
        permissionResourceService.updateByDataId(entity, dataId)
    }

    @Override
    protected List<String> addCheckProperties() {
        return ["resourceId", "resourceName", "resourceValue", "resourceType"]
    }

    @Override
    protected List<String> updateCheckProperties() {
        return ["resourceId", "resourceName", "resourceValue", "resourceType"]
    }

    @Override
    protected void addCustomCheck(Map<String, Object> errorMap, AddPermissionResourceParam param) {
        checkResourceType(errorMap, param)
    }

    @Override
    protected void updateCustomCheck(Map<String, Object> errorMap, UpdatePermissionResourceParam param) {
        checkResourceType(errorMap, param)
    }

    private void checkResourceType(Map<String, Object> errorMap, Param param) {
        RequestParamUtils.checkWithClosures(errorMap, param,
                ["resourceType": { PermissionResource.ResourceType.hasType(it as Integer) }]
        )
    }

}
