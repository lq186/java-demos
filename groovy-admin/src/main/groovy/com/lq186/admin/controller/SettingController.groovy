package com.lq186.admin.controller

import com.lq186.admin.common.ResponseBean
import com.lq186.admin.entity.Setting
import com.lq186.admin.service.SettingService
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
@RequestMapping(value = "/api/settings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class SettingController extends BaseController<Setting> {

    @Resource
    SettingService settingService

    @GetMapping
    def query(@RequestParam(name = "itemText", required = false) String itemText) {
        query {
            settingService.findPage(itemText)
        }
    }

    @PostMapping
    def add(@ModelAttribute Setting setting) {
        add(setting) {
            settingService.save(setting)
        }
    }

    @GetMapping("/{dataId}")
    def find(@PathVariable("dataId") String dataId) {
        find(dataId) {
            settingService.findByDataId(dataId)
        }
    }

    @PutMapping("/{dataId}")
    def updateByDataId(@PathVariable("dataId") String dataId, @ModelAttribute Setting setting) {
        update(setting, dataId) {
            settingService.updateByDataId(setting, dataId)
        }
    }

    @DeleteMapping("/{dataId}")
    def deleteByDataId(@PathVariable("dataId") String dataId) {
        delete(dataId) {
            settingService.deleteByDataId(dataId)
        }
    }

    @GetMapping("/refresh")
    def refresh() {
        return ResponseBean.success()
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
