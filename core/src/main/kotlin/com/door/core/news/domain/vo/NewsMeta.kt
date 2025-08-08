package com.door.core.news.domain.vo

import com.door.core.global.NewsProvider
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

data class NewsMeta(
    val newsProvider: NewsProvider,
    val newsPublishedTime: LocalDateTime,
    val sourceUrl: String,
) {

    companion object {
        fun of(
            newsProvider: NewsProvider,
            newsPublishedTime: OffsetDateTime,
            sourceUrl: String,
        ) = NewsMeta(
            newsProvider = newsProvider,
            newsPublishedTime = newsPublishedTime
                .atZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime(),
            sourceUrl = sourceUrl
        )

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
