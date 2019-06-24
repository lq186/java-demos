package com.lq186.admin.service.impl

import com.lq186.admin.consts.Errors
import com.lq186.admin.context.PageRequestContext
import com.lq186.admin.entity.Dictionary
import com.lq186.admin.exception.CodeException
import com.lq186.admin.repo.DictionaryRepo
import com.lq186.admin.service.DictionaryService
import com.lq186.admin.util.BeanUtils
import com.lq186.admin.util.RandomUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

import javax.annotation.Resource

@Service
class DictionaryServiceImpl implements DictionaryService {

    @Resource
    DictionaryRepo dictionaryRepo

    @Override
    Page<Dictionary> findPage(String queryText) {
        if (queryText) {
            def likeText = "$queryText%"
            return dictionaryRepo.findAllByValueLikeOrDisplayLikeOrGroupLike(likeText, likeText, likeText, PageRequestContext.getPageRequest())
        } else {
            return dictionaryRepo.findAll(PageRequestContext.getPageRequest())
        }
    }

    @Override
    String add(Dictionary dictionary) {
        if (!dictionary || !dictionary.value || !dictionary.display) {
            return "false"
        }

        if (!dictionary.group) {
            dictionary.group = Dictionary.DEFAULT_GROUP
        }

        def oldDictionary = dictionaryRepo.findByGroupAndValue(dictionary.group, dictionary.value)
        if (oldDictionary) {
            throw new CodeException(Errors.Code.DATA_EXISTS, Errors.Msg.DATA_EXISTS_MSG)
        }

        dictionary.dataId = RandomUtils.uuid()
        dictionaryRepo.save(dictionary)
        return dictionary.dataId
    }

    @Override
    void updateByDataId(Dictionary dictionary, String dataId) {
        if (!dictionary || !dataId || !dictionary.value || !dictionary.display) {
            return
        }

        def oldDictionary = dictionaryRepo.findByDataId(dataId)
        if (!oldDictionary) {
            throw new CodeException(Errors.Code.DATA_NOT_EXISTS, Errors.Msg.DATA_NOT_EXISTS_MSG)
        }

        if (!dictionary.group) {
            dictionary.group = Dictionary.DEFAULT_GROUP
        }

        def groupAndValueDictionary = dictionaryRepo.findByGroupAndValue(dictionary.group, dictionary.value)
        if (groupAndValueDictionary && groupAndValueDictionary.dataId != dataId) {
            throw new CodeException(Errors.Code.DATA_EXISTS, Errors.Msg.DATA_EXISTS_MSG)
        }

        BeanUtils.copyPropertiesIfNoneNullAndNotEquals(dictionary, oldDictionary, ["value", "display", "group"])
        dictionaryRepo.saveAndFlush(oldDictionary)
    }

    @Override
    void deleteByDataIds(String[] dataIds) {
        if (dataIds) {
            dictionaryRepo.deleteByDataIdIn(dataIds)
        }
    }

    @Override
    Map<String, Map<String, String>> findAll() {
        List<Dictionary> dictionaryList = dictionaryRepo.findAll(new Sort(Sort.Direction.ASC, "display"))
        if (dictionaryList) {
            return dictionaryList.groupBy { it.group }.collectEntries {
                def map = it.value.collectEntries {
                    [(it.value): it.display]
                }
                [(it.key): map]
            }
        }
        return [:]
    }

    @Override
    Dictionary findByDataId(String dataId) {
        return dictionaryRepo.findByDataId(dataId)
    }
}
