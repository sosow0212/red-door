package com.door.batch.helper.annotations

import com.door.batch.helper.DatabaseCleanupListener
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@TestExecutionListeners(
    listeners = [
        DependencyInjectionTestExecutionListener::class,
        DatabaseCleanupListener::class,
    ],
)
@SpringBatchTest
@SpringBootTest
annotation class IntegrationTest
