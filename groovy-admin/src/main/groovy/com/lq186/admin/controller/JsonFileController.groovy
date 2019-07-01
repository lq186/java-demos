package com.lq186.admin.controller

import com.lq186.admin.service.DictionaryService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

@RestController
@RequestMapping(value = "/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class JsonFileController {

    @Resource
    DictionaryService dictionaryService

    @GetMapping("/dictionaries.json")
    def dictionaries() {
        return dictionaryService.findAll()
    }
}
