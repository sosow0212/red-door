package com.door.core.news.adapter.persistence.command

import com.door.core.news.domain.News
import com.door.core.news.domain.vo.AiOverview
import com.door.core.news.domain.vo.Content
import com.door.core.news.domain.vo.NewsMeta
import org.springframework.stereotype.Component

@Component
class NewsJpaMapper {

    fun toDomain(newsJpaEntity: NewsJpaEntity): News {
        val translatedContent = if (newsJpaEntity.translatedTitle != null && newsJpaEntity.translatedContent != null) {
            Content(
                title = newsJpaEntity.translatedTitle,
                content = newsJpaEntity.translatedContent
            )
        } else {
            null
        }

        val aiOverview = if (newsJpaEntity.overview != null &&
            newsJpaEntity.sentimentType != null &&
            newsJpaEntity.sentimentScore != null
        ) {
            AiOverview(
                overview = newsJpaEntity.overview,
                sentimentType = newsJpaEntity.sentimentType,
                sentimentScore = newsJpaEntity.sentimentScore,
                targetCategories = newsJpaEntity.targetCategories
            )
        } else {
            null
        }

        return News(
            id = newsJpaEntity.id ?: 0L,
            newsMeta = NewsMeta(
                newsJpaEntity.newsProvider,
                newsJpaEntity.newsPublishedTime,
                newsJpaEntity.sourceUrl,
            ),
            scrapedTime = newsJpaEntity.scrapedTime,
            originalContent = Content(
                title = newsJpaEntity.originalTitle,
                content = newsJpaEntity.originalContent
            ),
            translatedContent = translatedContent,
            aiOverView = aiOverview
        )
    }

    fun toEntity(news: News): NewsJpaEntity {
        return NewsJpaEntity(
            id = if (news.id == 0L) null else news.id,
            newsProvider = news.newsMeta.newsProvider,
            newsPublishedTime = news.newsMeta.newsPublishedTime,
            sourceUrl = news.newsMeta.sourceUrl,
            scrapedTime = news.scrapedTime,
            originalTitle = news.originalContent.title,
            originalContent = news.originalContent.content,
            translatedTitle = news.translatedContent?.title,
            translatedContent = news.translatedContent?.content,
            overview = news.aiOverView?.overview,
            sentimentType = news.aiOverView?.sentimentType,
            sentimentScore = news.aiOverView?.sentimentScore,
            targetCategories = news.aiOverView?.targetCategories ?: listOf()
        )
    }
}
