package com.door.web.config.module

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@PropertySource(
    value = [
        "classpath:application-core-\${spring.profiles.active}.yml",
    ],
    factory = YamlPropertySourceFactory::class,
)
@Configuration
class PropertySourceScanConfig
