package com.door.core.news.domain

import com.door.core.global.NewsProvider
import com.door.core.news.domain.vo.AiOverview
import com.door.core.news.domain.vo.Content
import com.door.core.news.domain.vo.TargetCategory
import java.time.LocalDateTime

data class News(
    val id: Long = 0L,
    val newsProvider: NewsProvider,
    val scrapedTime: LocalDateTime = LocalDateTime.now(),
    val originalContent: Content,

    val translatedContent: Content?,
    val aiOverView: AiOverview?,
) {

    fun isContainsNewsProviderAndCategories(
        categories: List<TargetCategory>,
        providers: List<NewsProvider>,
    ): Boolean {
        val isMatchedCategory = this.aiOverView
            ?.isMatchedCategory(categories)
            ?: false

        return providers.contains(this.newsProvider)
                && isMatchedCategory
    }
}
