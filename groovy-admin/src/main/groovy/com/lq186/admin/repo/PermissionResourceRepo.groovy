package com.lq186.admin.repo

import com.lq186.admin.entity.PermissionResource
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PermissionResourceRepo extends JpaRepository<PermissionResource, Long>,
        JpaSpecificationExecutor<PermissionResource> {

    Integer countByParentResourceDataId(String parentResourceDataId)

    @Query('''
        update
            PermissionResource pr
        set
            pr.serialNumber = pr.serialNumber + 1
        where
            pr.parentResourceDataId = :parentResourceDataId
            and
            pr.serialNumber >= :serialNumber
        ''')
    @Modifying
    void addSerialNumberWhenBigger(@Param("serialNumber") Integer serialNumber,
                                   @Param("parentResourceDataId") String parentResourceDataId)

    PermissionResource findByResourceId(String resourceId)

    PermissionResource findByDataId(String dataId)

    @Query('''
        update
            PermissionResource pr
        set
            pr.serialNumber = pr.serialNumber - 1
        where
            pr.parentResourceDataId = :parentResourceDataId
            and
            pr.serialNumber > :serialNumber
        ''')
    @Modifying
    void subSerialNumberWhenBigger(@Param("serialNumber") Integer serialNumber,
                                   @Param("parentResourceDataId") String parentResourceDataId)

    @Query('''
        delete from
            PermissionResource pr
        where
            pr.dataId in (:dataIds)
        ''')
    @Modifying
    void deleteByDataIdIn(@Param("dataIds") String[] dataIds)

    @Query('''
        from
            PermissionResource pr
        where
            pr.parentResourceDataId in (:parentResourceDataIds)
        ''')
    List<PermissionResource> findAllByParentResourceDataIdIn(@Param("parentResourceDataIds") String[] parentResourceDataIds)

    @Query('''
        select pr from
            PermissionResource pr 
            left join
            RelRoleResource rel
            on pr.dataId = rel.resourceDataId
        where
            rel.roleDataId in (:roleDataIds)
        ''')
    Page<PermissionResource> findAllByRoleDataIdInFromRel(@Param("roleDataIds") String[] roleDataIds, Pageable pageable)

    @Query('''
        select pr from
            PermissionResource pr 
            left join
            RelRoleResource rel
            on pr.dataId = rel.resourceDataId
        where
            rel.roleDataId in (:roleDataIds)
        ''')
    List<PermissionResource> findAllByRoleDataIdInFromRel(@Param("roleDataIds") String[] roleDataIds)
}
