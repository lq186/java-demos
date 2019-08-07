package com.lq186.admin.repo

import com.lq186.admin.model.entity.RelRoleResource
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RelRoleResourceRepo extends JpaRepository<RelRoleResource, Long> {

    @Query('''
        delete from
            RelRoleResource rel
        where
            rel.roleDataId in (:roleDataIds)
        ''')
    @Modifying
    void deleteAllByRoleDataIdIn(@Param("roleDataIds") String[] roleDataIds)
}
