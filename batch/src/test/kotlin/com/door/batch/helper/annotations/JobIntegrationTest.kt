package com.door.batch.helper.annotations

import com.door.batch.helper.DatabaseCleanupListener
import com.door.batch.helper.DynamicImportSelector
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@TestExecutionListeners(
    listeners = [
        DependencyInjectionTestExecutionListener::class,
        DatabaseCleanupListener::class,
    ]
)
@Import(DynamicImportSelector::class)
@SpringBatchTest
@SpringBootTest
annotation class JobIntegrationTest(

    val jobClasses: Array<KClass<*>>
)
