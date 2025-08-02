package com.door.web.config.module

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(
    basePackages = [
        "com.door.core",
    ],
)
class ModuleConfig
