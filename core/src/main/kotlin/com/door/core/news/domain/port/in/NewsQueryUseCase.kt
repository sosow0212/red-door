package com.door.core.news.domain.port.`in`

import com.door.core.news.domain.Newses
import com.door.core.global.NewsProvider
import com.door.core.news.domain.vo.SentimentType
import com.door.core.news.domain.vo.TargetCategory
import java.time.LocalDateTime

interface NewsQueryUseCase {

    fun findAllByFilters(request: NewsQueryRequest): Newses
}

data class NewsQueryRequest(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val sentimentType: SentimentType,
    val newsProviders: List<NewsProvider>,
    val categories: List<TargetCategory>,
)
