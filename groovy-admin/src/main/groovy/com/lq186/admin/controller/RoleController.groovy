package com.lq186.admin.controller

import com.lq186.admin.common.ResponseBean
import com.lq186.admin.entity.PermissionResource
import com.lq186.admin.entity.Role
import com.lq186.admin.service.RoleService
import com.lq186.admin.util.RequestParamUtils
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

@RestController
@RequestMapping(value = "/api/roles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class RoleController extends BaseController<Role> {

    @Resource
    RoleService roleService

    @GetMapping
    def query(@RequestParam(name = "roleName", required = false) String roleName) {
        query {
            roleService.findPage(roleName)
        }
    }

    @PostMapping
    def add(@RequestParam(name = "roleName") String roleName,
            @RequestParam(name = "resourceDataIds", required = false) String[] resourceDataIds) {
        add(new Role(roleName: roleName)) {
            roleService.save(roleName, resourceDataIds)
        }
    }

    @GetMapping("/{dataId}")
    def find(@PathVariable("dataId") String dataId) {
        find(dataId) {
            roleService.findByDataId(dataId)
        }
    }

    @PutMapping("/{dataId}")
    def update(@PathVariable("dataId") String dataId,
               @RequestParam(name = "roleName", required = false) String roleName) {
        update(new Role(roleName: roleName), dataId) {
            roleService.updateByDataId(roleName, dataId)
        }
    }

    @GetMapping("/{dataId}/resources")
    def findRelResources(@PathVariable("dataId") String dataId,
                         @RequestParam(name = "pageable", required = false) Boolean pageable) {
        RequestParamUtils.checkDataId(dataId)
        if (pageable) {
            Page<PermissionResource> page = roleService.findResourcesPageByDataIdIn(dataId)
            return ResponseBean.success(page)
        } else {
            List<PermissionResource> resourceList = roleService.findResourcesByDataIdIn(dataId)
            return ResponseBean.success(resourceList)
        }
    }

    @GetMapping("/resources")
    def findRelResourcesByDataIds(@RequestParam("dataIds") String[] dataIds,
                                  @RequestParam(name = "pageable", required = false) Boolean pageable) {
        RequestParamUtils.checkParamNoneEmpty("dataIds", dataIds)
        if (pageable) {
            Page<PermissionResource> page = roleService.findResourcesPageByDataIdIn(dataIds)
            return ResponseBean.success(page)
        } else {
            List<PermissionResource> resourceList = roleService.findResourcesByDataIdIn(dataIds)
            return ResponseBean.success(resourceList)
        }
    }

    @PutMapping("/{dataId}/resources")
    def updateRelResources(@PathVariable("dataId") String dataId,
                           @RequestParam(name = "resourceDataIds", required = false) String[] resourceDataIds) {
        RequestParamUtils.checkDataId(dataId)
        roleService.updateRelResourcesByDataId(resourceDataIds, dataId)
        return ResponseBean.success()
    }

    @DeleteMapping("/{dataId}")
    def delete(@PathVariable("dataId") String dataId) {
        delete(dataId) {
            roleService.deleteByDataId(dataId)
        }
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
