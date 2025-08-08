package com.door.core.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan("com.door.core.news.adapter.requester.properties")
class PropertiesScanConfig
