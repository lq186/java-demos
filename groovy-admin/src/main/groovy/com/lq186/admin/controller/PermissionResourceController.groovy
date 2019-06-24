package com.lq186.admin.controller

import com.lq186.admin.common.ResponseBean
import com.lq186.admin.entity.PermissionResource
import com.lq186.admin.service.PermissionResourceService
import com.lq186.admin.util.RequestParamUtils
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

@RestController
@RequestMapping(value = "/api/resources", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class PermissionResourceController extends BaseController<PermissionResource> {

    @Resource
    PermissionResourceService permissionResourceService

    @GetMapping
    def query(@RequestParam(name = "queryText", required = false) String queryText,
              @RequestParam(name = "resourceType", required = false) Integer resourceType) {
        query {
            permissionResourceService.findPage(queryText, resourceType)
        }
    }

    @PostMapping
    def add(@ModelAttribute("resource") PermissionResource resource) {
        add(resource) {
            permissionResourceService.save(resource)
        }
    }

    @GetMapping("/{dataId}")
    def find(@PathVariable("dataId") String dataId) {
        find(dataId) {
            permissionResourceService.findByDataId(dataId)
        }
    }

    @PutMapping("/{dataId}")
    def update(@PathVariable("dataId") String dataId, @ModelAttribute("resource") PermissionResource resource) {
        update(resource, dataId) {
            permissionResourceService.updateByDataId(resource, dataId)
        }
    }

    @DeleteMapping("/{dataId}")
    def delete(@PathVariable("dataId") String dataId) {
        delete(dataId) {
            permissionResourceService.deleteByDataId(dataId)
        }
    }

    @GetMapping("/max_serial_number")
    def maxSerialNumber(@RequestParam(name = "dataId", required = false) String dataId) {
        def maxSerialNumber = permissionResourceService.maxSerialNumber(dataId)
        return ResponseBean.success(maxSerialNumber)
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
    protected void addCustomCheck(Map<String, Object> errorMap, PermissionResource resource) {
        checkResourceType(errorMap, resource)
    }

    @Override
    protected void updateCustomCheck(Map<String, Object> errorMap, PermissionResource resource) {
        checkResourceType(errorMap, resource)
    }

    private void checkResourceType(Map<String, Object> errorMap, PermissionResource resource) {
        RequestParamUtils.checkWithClosures(errorMap, resource,
                ["resourceType": { PermissionResource.ResourceType.hasType(it as Integer) }]
        )
    }

}
