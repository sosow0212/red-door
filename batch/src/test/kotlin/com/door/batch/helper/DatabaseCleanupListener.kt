package com.door.batch.helper

import jakarta.persistence.EntityManager
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

class DatabaseCleanupListener : AbstractTestExecutionListener() {
    override fun beforeTestExecution(testContext: TestContext) {
        val applicationContext = testContext.applicationContext
        val transactionManager = applicationContext.getBean(PlatformTransactionManager::class.java)
        val entityManager = applicationContext.getBean(EntityManager::class.java)

        cleanupDatabase(entityManager, transactionManager)
    }

    private fun cleanupDatabase(
        entityManager: EntityManager,
        transactionManager: PlatformTransactionManager,
    ) {
        TransactionTemplate(transactionManager).executeWithoutResult {
            val tableNames =
                entityManager
                    .createNativeQuery(
                        "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'",
                    ).resultList
                    .filterIsInstance<String>()

            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
            tableNames.forEach { table ->
                entityManager.createNativeQuery("TRUNCATE TABLE \"$table\"").executeUpdate()
            }
            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
        }
    }
}

