package com.door.web.config.module

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.io.support.DefaultPropertySourceFactory
import org.springframework.core.io.support.EncodedResource

class YamlPropertySourceFactory : DefaultPropertySourceFactory() {

    override fun createPropertySource(
        name: String?,
        resource: EncodedResource,
    ): PropertiesPropertySource {
        val factory =
            YamlPropertiesFactoryBean().apply {
                setResources(resource.resource)
            }
        return PropertiesPropertySource(resource.resource.filename ?: "unknown", factory.getObject()!!)
    }
}
