package com.door.core.news.domain.vo

import com.door.core.global.NewsProvider
import java.time.LocalDateTime

data class NewsMeta(
    val newsProvider: NewsProvider,
    val newsPublishedTime: LocalDateTime,
    val sourceUrl: String,
) {

    companion object {
        fun of(
            newsProvider: NewsProvider,
            newsPublishedTime: String,
            sourceUrl: String,
        ) = NewsMeta(
            newsProvider = newsProvider,
            newsPublishedTime = LocalDateTime.parse(newsPublishedTime),
            sourceUrl = sourceUrl
        )
    }
}
