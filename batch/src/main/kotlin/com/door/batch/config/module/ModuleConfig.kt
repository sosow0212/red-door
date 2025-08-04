package com.door.batch.config.module

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(
    basePackages = [
        "com.door.core",
    ],
)
class ModuleConfig
