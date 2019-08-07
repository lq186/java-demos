package com.lq186.admin.repo

import com.lq186.admin.model.entity.Dictionary
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DictionaryRepo extends JpaRepository<Dictionary, Long> {

    Page<Dictionary> findAllByValueLikeOrDisplayLikeOrGroupLike(String valueLike, String displayLike,
                                                                String groupLike, Pageable pageable)

    Dictionary findByGroupAndValue(String group, String value)

    Dictionary findByDataId(String dataId)

    @Query('''
        delete from
            Dictionary d
        where
            d.dataId in (:dataIds)
        ''')
    void deleteByDataIdIn(String[] dataIds)
}
