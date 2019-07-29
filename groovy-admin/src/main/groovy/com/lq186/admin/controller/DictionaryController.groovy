package com.lq186.admin.controller

import com.lq186.admin.entity.Dictionary
import com.lq186.admin.service.DictionaryService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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
@Api(value = "/api/dictionaries", tags = "字典信息模块")
@RequestMapping(value = "/api/dictionaries", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class DictionaryController extends BaseController {

    @Resource
    DictionaryService dictionaryService

    @GetMapping
    @ApiOperation(value = "分页查询字典信息", notes = "This is notes.")
    def query(@RequestParam(name = "queryText", required = false) String queryText) {
        query {
            dictionaryService.findPage(queryText)
        }
    }

    @PostMapping
    def add(@ModelAttribute("dictionary") Dictionary dictionary) {
        add(dictionary) {
            dictionaryService.add(dictionary)
        }
    }

    @GetMapping("/{dataId}")
    def find(@PathVariable("dataId") String dataId) {
        find(dataId) {
            dictionaryService.findByDataId(dataId)
        }
    }

    @PutMapping("/{dataId}")
    def update(@PathVariable("dataId") String dataId,
               @ModelAttribute("dictionary") Dictionary dictionary) {
        update(dictionary, dataId) {
            dictionaryService.updateByDataId(dictionary, dataId)
        }
    }

    @DeleteMapping("/{dataId}")
    def delete(@PathVariable("dataId") String dataId) {
        delete(dataId) {
            dictionaryService.deleteByDataIds(dataId)
        }
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
