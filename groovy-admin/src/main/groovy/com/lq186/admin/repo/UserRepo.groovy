package com.lq186.admin.repo

import com.lq186.admin.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepo extends JpaRepository<User, Long> {

    Page<User> findAllByUseState(Integer useState, Pageable pageable)

    Page<User> findAllByUsernameLikeOrDisplayNameLike(String usernameLike, String displayNameLike, Pageable pageable)

    @Query('''
            from 
                User u 
            where 
                (u.username like :usernameLike or u.displayName like :displayNameLike) 
                and u.useState = :useState
           ''')
    Page<User> findAllBy(@Param("usernameLike") String usernameLike,
                         @Param("displayNameLike") String displayNameLike,
                         @Param("useState") Integer useState,
                         Pageable pageable)

    User findByDataId(String dataId)

    @Query('''
            update 
                User u 
            set 
                u.displayName = :#{#user.displayName}, 
                u.useState = :#{#user.useState},
                u.superUser = :#{#user.superUser},
                u.activeTime = :#{#user.activeTime},
                u.invalidTime = :#{#user.invalidTime}
            where
                u.dataId = :#{#user.dataId}
           ''')
    @Modifying
    int updateByDataId(@Param("user") User user)

    User findByUsername(String username)

    @Query("delete from User u where u.dataId in (:dataIds)")
    @Modifying
    void deleteByDataIdIn(@Param("dataIds") String dataIds)

    @Query('''
        update
            User u
        set
            u.passwordMd5 = :passwordMd5,
            u.passwordSalt = :passwordSalt
        where
            u.dataId = :dataId
        ''')
    @Modifying
    void updatePasswordMd5ByDataId(String passwordMd5, String passwordSalt, String dataId)

}
