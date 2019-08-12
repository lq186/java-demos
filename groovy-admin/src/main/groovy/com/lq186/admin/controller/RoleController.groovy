package com.lq186.admin.controller

import com.lq186.admin.annotation.ApiImplicitParamToken
import com.lq186.admin.annotation.ApiPageableParams
import com.lq186.admin.common.ResponseBean
import com.lq186.admin.model.entity.PermissionResource
import com.lq186.admin.model.entity.Role
import com.lq186.admin.model.params.AddRoleParam
import com.lq186.admin.model.params.UpdateRoleParam
import com.lq186.admin.model.views.PageData
import com.lq186.admin.model.views.PermissionResourceView
import com.lq186.admin.model.views.RoleView
import com.lq186.admin.service.RoleService
import com.lq186.admin.util.BeanUtils
import com.lq186.admin.util.PageUtils
import com.lq186.admin.util.RequestParamUtils
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
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
@Api(value = "/api/roles", tags = "角色信息模块")
@RequestMapping(value = "/api/roles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class RoleController extends BaseController<RoleView, Role, AddRoleParam, UpdateRoleParam> {

    @Resource
    RoleService roleService

    @GetMapping
    @ApiOperation("分页查询角色信息")
    @ApiImplicitParams([
            @ApiImplicitParam(name = "token", paramType = "header", value = "access_token", dataTypeClass = String.class),
            @ApiImplicitParam(name = "roleName", value = "角色名称"),
            @ApiImplicitParam(name = "page", paramType = "query", value = "页码", defaultValue = "1", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", paramType = "query", value = "页面记录数", defaultValue = "10", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sort", paramType = "query", value = "排序方式[prop:direction,...]", required = false, dataTypeClass = String.class)
    ])
    ResponseBean<PageData<RoleView>> query(@RequestParam(name = "roleName", required = false) String roleName) {
        query {
            roleService.findPage(roleName)
        }
    }

    @PostMapping
    @ApiOperation("新增角色信息")
    @ApiImplicitParamToken
    ResponseBean<String> add(@RequestBody AddRoleParam param) {
        super.add(param)
    }

    @GetMapping("/{id}")
    @ApiOperation("获取角色信息")
    @ApiImplicitParamToken
    ResponseBean<RoleView> find(@PathVariable("id") String dataId) {
        findEntity(dataId) {
            roleService.findByDataId(dataId)
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("更新角色信息")
    @ApiImplicitParamToken
    ResponseBean<Void> update(@PathVariable("id") String dataId, @RequestBody UpdateRoleParam param) {
        super.update(param, dataId)
    }

    @GetMapping("/{id}/resources/pageable")
    @ApiOperation("分页获取角色下的权限资源信息")
    @ApiPageableParams
    ResponseBean<PageData<PermissionResourceView>> findResourcesPageable(@PathVariable("id") String dataId) {
        RequestParamUtils.checkDataId(dataId)
        Page<PermissionResource> page = roleService.findResourcesPageByDataIdIn(dataId)
        return ResponseBean.success(page, PermissionResourceView.class)
    }

    @GetMapping("/{id}/resources")
    @ApiOperation("获取角色下的权限资源信息")
    @ApiImplicitParamToken
    ResponseBean<List<PermissionResourceView>> findResources(@PathVariable("id") String dataId) {
        RequestParamUtils.checkDataId(dataId)
        List<PermissionResource> resourceList = roleService.findResourcesByDataIdIn(dataId)
        return ResponseBean.success(resourceList, PermissionResourceView.class)
    }

    @GetMapping("/resources/pageable")
    @ApiOperation("分页获取角色下的权限资源信息")
    @ApiPageableParams
    ResponseBean<PageData<PermissionResourceView>> findRelResourcesByDataIdsPageable(@RequestParam("ids") String[] dataIds) {
        RequestParamUtils.checkParamNoneEmpty("ids", dataIds)
        Page<PermissionResource> page = roleService.findResourcesPageByDataIdIn(dataIds)
        return ResponseBean.success(page, PermissionResourceView.class)
    }

    @GetMapping("/resources")
    @ApiOperation("获取角色下的权限资源信息")
    @ApiImplicitParamToken
    ResponseBean<List<PermissionResourceView>> findRelResourcesByDataIds(@RequestParam("ids") String[] dataIds) {
        RequestParamUtils.checkParamNoneEmpty("ids", dataIds)
        List<PermissionResource> resourceList = roleService.findResourcesByDataIdIn(dataIds)
        return ResponseBean.success(resourceList, PermissionResourceView.class)
    }

    @PutMapping("/{id}/resources")
    @ApiOperation("更新角色下的权限资源信息")
    @ApiImplicitParamToken
    ResponseBean<Void> updateRelResources(@PathVariable("id") String dataId,
                           @RequestBody List<String> resourceDataIds) {
        RequestParamUtils.checkDataId(dataId)
        roleService.updateRelResourcesByDataId(resourceDataIds, dataId)
        return ResponseBean.success()
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除角色信息")
    @ApiImplicitParamToken
    ResponseBean<Void> delete(@PathVariable("id") String dataId) {
        delete(dataId) {
            roleService.deleteByDataId(dataId)
        }
    }

    @Override
    String saveEntity(AddRoleParam param, Role entity) {
        return roleService.save(param.roleName, param.resourceDataIds)
    }

    @Override
    void updateEntity(UpdateRoleParam param, Role entity, String dataId) {
        roleService.updateByDataId(param.roleName, dataId)
    }

    @Override
    protected List<String> addCheckProperties() {
        return ["roleName"]
    }

    @Override
    protected List<String> updateCheckProperties() {
        return ["roleName"]
    }
}
