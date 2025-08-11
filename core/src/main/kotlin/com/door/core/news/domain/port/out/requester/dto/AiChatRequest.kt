package com.door.core.news.domain.port.out.requester.dto

data class AiChatRequest(
    val newsItems: List<NewsItemRequest>,
) {
    data class NewsItemRequest(
        val title: String,
        val content: String,
    )
}
