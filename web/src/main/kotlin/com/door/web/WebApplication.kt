package com.door.web

import com.door.core.config.ApplicationComponentScanConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@ConfigurationPropertiesScan
@Import(ApplicationComponentScanConfig::class)
@SpringBootApplication
class WebApplication

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}
