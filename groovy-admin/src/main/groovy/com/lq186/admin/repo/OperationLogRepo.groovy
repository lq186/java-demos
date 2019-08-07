package com.lq186.admin.repo

import com.lq186.admin.model.entity.OperationLog
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OperationLogRepo extends JpaRepository<OperationLog, Long> {

    @Query('''
        from
            OperationLog log
        where
            log.operationData like :logText
            or
            log.operationResult like :logText
            or
            log.operationNotes like :logText
        ''')
    Page<OperationLog> findAllByLogTextLike(@Param("logText") String logText, Pageable pageable)

    @Query('''
        from
            OperationLog log
        where
            log.operationType = :operationType
            and (
            log.operationData like :logText
            or
            log.operationResult like :logText
            or
            log.operationNotes like :logText )
        ''')
    Page<OperationLog> findAllByOperationTypeAndLogTextLike(@Param("operationType") int operationType,
                                                            @Param("logText") String logText, Pageable pageable)

    Page<OperationLog> findAllByOperationType(int operationType, Pageable pageable)

    @Query('''
        delete from
            OperationLog log
        where
            log.invalidTime <= :invalidTime
        ''')
    @Modifying
    void deleteByInvalidTimeLess(@Param("invalidTime") long invalidTime)

    OperationLog findByDataId(String dataId)
}