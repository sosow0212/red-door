package com.door.core.news.fixture

import com.door.core.news.domain.News
import com.door.core.news.domain.vo.AiOverview
import com.door.core.news.domain.vo.Content
import com.door.core.global.NewsProvider
import com.door.core.news.domain.vo.SentimentType
import com.door.core.news.domain.vo.TargetCategory
import java.time.LocalDateTime

class NewsFixture {

    companion object {
        fun 인공지능_오버뷰_생성_비트코인_긍정적_30프로() =
            AiOverview(
                overview = "overview",
                sentimentType = SentimentType.POSITIVE,
                sentimentScore = 0.3,
                targetCategories = listOf(TargetCategory.BTC),
            )

        fun 뉴스_생성_블룸버그_비트코인() =
            News(
                id = 1,
                newsProvider = NewsProvider.BLOOMBERG,
                scrapedTime = LocalDateTime.now(),
                originalContent = Content(
                    title = "originalTitle",
                    content = "originalContent",
                ),
                translatedContent = Content(
                    title = "translatedTitle",
                    content = "translatedContent",
                ),
                aiOverView = 인공지능_오버뷰_생성_비트코인_긍정적_30프로()
            )

        fun 뉴스_생성_블룸버그_테슬라() =
            News(
                id = 1,
                newsProvider = NewsProvider.BLOOMBERG,
                scrapedTime = LocalDateTime.now(),
                originalContent = Content(
                    title = "originalTitle",
                    content = "originalContent",
                ),
                translatedContent = Content(
                    title = "translatedTitle",
                    content = "translatedContent",
                ),
                aiOverView = 인공지능_오버뷰_생성_비트코인_긍정적_30프로()
                    .copy(targetCategories = listOf(TargetCategory.TSLA))
            )
    }
}
