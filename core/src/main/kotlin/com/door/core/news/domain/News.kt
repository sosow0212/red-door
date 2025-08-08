package com.door.core.news.domain

import com.door.core.global.NewsProvider
import com.door.core.news.domain.vo.AiOverview
import com.door.core.news.domain.vo.Content
import com.door.core.news.domain.vo.NewsMeta
import com.door.core.news.domain.vo.TargetCategory
import java.time.LocalDateTime

data class News(
    val id: Long = 0L,
    val newsMeta: NewsMeta,
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

        return providers.contains(this.newsMeta.newsProvider)
                && isMatchedCategory
    }

    companion object {
        fun createWithoutAI(
            newsMeta: NewsMeta,
            scrapedTime: LocalDateTime = LocalDateTime.now(),
            originalContent: Content,
        ) = News(
            id = 0L,
            newsMeta = newsMeta,
            scrapedTime = scrapedTime,
            originalContent = originalContent,
            translatedContent = null,
            aiOverView = null
        )
    }
}
