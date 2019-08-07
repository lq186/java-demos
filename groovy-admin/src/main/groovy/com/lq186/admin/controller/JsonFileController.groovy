package com.lq186.admin.controller

import com.lq186.admin.service.DictionaryService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

@RestController
@Api(value = "/json", description = "通用json文件模块")
@RequestMapping(value = "/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class JsonFileController {

    @Resource
    DictionaryService dictionaryService

    @GetMapping("/dictionaries.json")
    @ApiOperation("字典信息JSON文件")
    def dictionaries() {
        return dictionaryService.findAll()
    }
}
