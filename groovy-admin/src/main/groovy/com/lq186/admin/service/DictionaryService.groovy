package com.lq186.admin.service

import com.lq186.admin.model.entity.Dictionary
import org.springframework.data.domain.Page

interface DictionaryService {

    Page<Dictionary> findPage(String queryText)

    String add(Dictionary dictionary)

    void updateByDataId(Dictionary dictionary, String dataId)

    void deleteByDataIds(String[] dataIds)

    Map<String, Map<String, String>> findAll()

    Dictionary findByDataId(String dataId)
}