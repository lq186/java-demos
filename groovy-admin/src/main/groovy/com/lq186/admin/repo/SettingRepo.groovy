package com.lq186.admin.repo

import com.lq186.admin.model.entity.Setting
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SettingRepo extends JpaRepository<Setting, Long> {

    @Query('''
        from
            Setting s
        where
            s.itemGroup like :itemText
            or
            s.itemKey like :itemText
            or
            s.itemValue like :itemText
            or
            s.itemDescription like :itemText
        ''')
    Page<Setting> findPageByItemAllLike(@Param("itemText") String itemText, Pageable pageable)

    Setting findByDataId(String dataId)

    @Query('''
        update
            Setting s
        set
            s.itemValue = :#{#setting.itemValue},
            s.itemDescription = :#{#setting.itemDescription}
        where
            s.dataId = :#{#setting.dataId}
        ''')
    @Modifying
    void updateItemValueAndItemDescriptionByDataId(@Param("setting") Setting setting)

    @Query('''
        delete from
            Setting s
        where
            s.dataId in (:dataIds)
        ''')
    @Modifying
    void deleteByDataIdIn(@Param("dataIds") String[] dataIds)

    Setting findByItemKey(String itemKey)
}