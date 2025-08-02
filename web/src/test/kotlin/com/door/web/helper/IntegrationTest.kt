package com.door.web.helper

import io.restassured.RestAssured
import jakarta.persistence.EntityManager
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.AbstractTestExecutionListener
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestExecutionListeners(
    listeners = [
        DependencyInjectionTestExecutionListener::class,
        DatabaseCleanupListener::class,
    ],
)
annotation class IntegrationTest

class DatabaseCleanupListener : AbstractTestExecutionListener() {
    override fun beforeTestExecution(testContext: TestContext) {
        val applicationContext = testContext.applicationContext
        val transactionManager = applicationContext.getBean(PlatformTransactionManager::class.java)
        val entityManager = applicationContext.getBean(EntityManager::class.java)

        applyRestAssuredPort(applicationContext.environment.getProperty("local.server.port"))
        cleanupDatabase(entityManager, transactionManager)
    }

    private fun applyRestAssuredPort(port: String?) {
        RestAssured.port =
            requireNotNull(port?.toIntOrNull()) {
                "RestAssured port 설정 불가: 'local.server.port'를 사용할 수 없습니다.."
            }
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
