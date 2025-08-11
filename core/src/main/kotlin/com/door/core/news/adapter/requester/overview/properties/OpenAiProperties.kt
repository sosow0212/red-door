package com.door.core.news.adapter.requester.overview.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "ai.openai.api")
data class OpenAiProperties(
    val baseUrl: String,
    val apiKey: String,
    val model: String,
) {

    init {
        require(baseUrl.isNotBlank()) { "📈 open-ai base-url must not be blank" }
        require(apiKey.isNotBlank()) { "📈 open-ai api-key must not be blank" }
        require(model.isNotBlank()) { "📈 open-ai model must not be blank" }
    }
}
