package com.lq186.admin.service

import com.lq186.admin.entity.Setting
import org.springframework.data.domain.Page

interface SettingService {

    Page<Setting> findPage(String itemText)

    String save(Setting setting)

    void updateByDataId(Setting setting, String dataId)

    Setting findByDataId(String dataId)

    void deleteByDataId(String[] dataIds)
}