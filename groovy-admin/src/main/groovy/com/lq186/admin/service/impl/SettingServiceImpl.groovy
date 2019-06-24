package com.lq186.admin.service.impl

import com.lq186.admin.consts.Errors
import com.lq186.admin.context.PageRequestContext
import com.lq186.admin.entity.Setting
import com.lq186.admin.exception.CodeException
import com.lq186.admin.repo.SettingRepo
import com.lq186.admin.service.SettingService
import com.lq186.admin.util.BeanUtils
import com.lq186.admin.util.RandomUtils
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.transaction.Transactional

@Service
@Transactional
class SettingServiceImpl implements SettingService {

    @Resource
    SettingRepo settingRepo

    @Override
    Page<Setting> findPage(String itemText) {
        if (itemText) {
            return settingRepo.findPageByItemAllLike("$itemText%", PageRequestContext.getPageRequest())
        } else {
            return settingRepo.findAll(PageRequestContext.getPageRequest())
        }
    }

    @Override
    String save(Setting setting) {

        Setting oldSetting = settingRepo.findByItemKey(setting.itemKey)
        if (oldSetting) {
            throw new CodeException(Errors.Code.DATA_EXISTS, Errors.Msg.DATA_EXISTS_MSG)
        }

        setting.dataId = RandomUtils.uuid()
        setting.itemGroup = setting.itemGroup ?: "Default"
        setting.itemValue = setting.itemValue ?: ""
        setting.itemDescription = setting.itemDescription ?: ""
        settingRepo.save(setting)

        return setting.dataId
    }

    @Override
    void updateByDataId(Setting setting, String dataId) {
        Setting oldSetting = settingRepo.findByDataId(dataId)
        if (!oldSetting) {
            throw new CodeException(Errors.Code.DATA_NOT_EXISTS, Errors.Msg.DATA_NOT_EXISTS_MSG)
        }

        if (setting.itemKey != oldSetting.itemKey) {
            throw new CodeException(Errors.Code.SETTING_ITEM_KEY_CAN_NOT_UPDATE, Errors.Msg.SETTING_ITEM_KEY_CAN_NOT_UPDATE_MSG)
        }

        BeanUtils.copyPropertiesIfNoneNullAndNotEquals(setting, oldSetting, [
                "itemValue",
                "itemDescription"
        ])

        settingRepo.updateItemValueAndItemDescriptionByDataId(oldSetting)
    }

    @Override
    void deleteByDataId(String[] dataIds) {
        settingRepo.deleteByDataIdIn(dataIds)
    }

    @Override
    Setting findByDataId(String dataId) {
        return settingRepo.findByDataId(dataId)
    }
}
