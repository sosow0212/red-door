package com.door.core.news.adapter.persistence.command

import com.door.core.global.BaseEntity
import com.door.core.global.NewsProvider
import com.door.core.news.domain.vo.SentimentType
import com.door.core.news.domain.vo.TargetCategory
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "news")
class NewsJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "news_provider", nullable = false)
    val newsProvider: NewsProvider,

    @Column(name = "news_published_time", nullable = false)
    val newsPublishedTime: LocalDateTime,

    @Column(name = "source_url", nullable = false)
    val sourceUrl: String,

    @Column(name = "scraped_time", nullable = false)
    val scrapedTime: LocalDateTime,

    @Column(name = "original_title", nullable = false)
    val originalTitle: String,

    @Lob
    @Column(name = "original_content", nullable = false, columnDefinition = "TEXT")
    val originalContent: String,

    @Column(name = "ai_translated_title", columnDefinition = "TEXT")
    val translatedTitle: String? = null,

    @Lob
    @Column(name = "ai_translated_content", columnDefinition = "TEXT")
    val translatedContent: String? = null,

    @Lob
    @Column(name = "ai_overview", columnDefinition = "TEXT")
    val overview: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "ai_sentiment_type")
    val sentimentType: SentimentType? = null,

    @Column(name = "ai_sentiment_score")
    val sentimentScore: Double? = null,

    @ElementCollection(targetClass = TargetCategory::class)
    @CollectionTable(
        name = "news_target_categories",
        joinColumns = [JoinColumn(name = "news_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))]
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    val targetCategories: List<TargetCategory> = listOf()
) : BaseEntity()
