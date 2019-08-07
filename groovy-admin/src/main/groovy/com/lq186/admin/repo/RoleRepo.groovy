package com.lq186.admin.repo

import com.lq186.admin.model.entity.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RoleRepo extends JpaRepository<Role, Long> {

    Page<Role> findAllByRoleNameLike(String roleName, Pageable pageable)

    Role findByRoleName(String roleName)

    Role findByDataId(String dataId)

    @Query('''
        delete from
            Role r
        where
            r.dataId in (:dataIds)
        ''')
    @Modifying
    void deleteByDataIdIn(@Param("dataIds") String[] dataIds)

    @Query('''
        select r from
            Role r
        left join RelUserRole rel
            on r.dataId = rel.roleDataId
        where
            rel.userDataId = :userDataId
        ''')
    List<Role> findAllByUserDataId(@Param("userDataId") String userDataId)

    @Query('''
        select r from
            Role r
        left join RelUserRole rel
            on r.dataId = rel.roleDataId
        where
            rel.userDataId = :userDataId
        ''')
    Page<Role> findPageByUserDataId(@Param("userDataId") String userDataId, Pageable pageable)
}