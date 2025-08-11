package com.door.core.news.adapter.requester.scrap.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "news.marketaux.api")
data class MarketAuxProperties(
    val baseUrl: String,
    val apiKey: String,
) {

    init {
        require(baseUrl.isNotBlank()) { "📈 marketaux base-url must not be blank" }
        require(apiKey.isNotBlank()) { "📈 marketaux api-key must not be blank" }
    }
}
