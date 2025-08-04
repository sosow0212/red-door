package com.door.batch.helper

import com.door.batch.helper.annotations.JobIntegrationTest
import org.springframework.context.annotation.ImportSelector
import org.springframework.core.type.AnnotationMetadata

class DynamicImportSelector : ImportSelector {

    override fun selectImports(importingClassMetadata: AnnotationMetadata): Array<String> {
        val value = importingClassMetadata
            .getAnnotationAttributes(JobIntegrationTest::class.java.name)
            ?.get("value")

        return when (value) {
            is Array<*> -> value
                .filterIsInstance<Class<*>>()
                .map { it.name }
                .toTypedArray()

            else -> emptyArray()
        }
    }
}
