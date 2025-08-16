package com.door.batch.news.scrap.job

import com.door.core.global.NewsProvider
import com.door.core.news.adapter.persistence.command.NewsJpaRepository
import com.door.core.news.adapter.requester.overview.NewsOpenAiAnalysisRequester
import com.door.core.news.domain.News
import com.door.core.news.domain.port.out.requester.NewsScrapRequester
import com.door.core.news.domain.port.out.requester.dto.AiChatResponse
import com.door.core.news.domain.vo.Content
import com.door.core.news.domain.vo.NewsMeta
import com.door.core.news.domain.vo.SentimentType
import com.door.core.news.domain.vo.TargetCategory
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.time.LocalDateTime
import kotlin.test.assertEquals

@SpringBatchTest
@SpringBootTest
class NewsScrapJobTest {

    @Autowired
    private lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @MockitoBean
    private lateinit var newsScrapRequester: NewsScrapRequester

    @MockitoBean
    private lateinit var newsOpenAiAnalysisRequester: NewsOpenAiAnalysisRequester

    @Autowired
    private lateinit var newsJpaRepository: NewsJpaRepository

    @Test
    fun `뉴스를 크롤링하고 ai 분석을 정상적으로 진행한다`() = runBlocking {
        // given
        val params = JobParametersBuilder()
            .addString("publishTimeAfter", "2025-08-14T00:00:00")
            .addLong("limit", 5)
            .addLong("runTime", System.currentTimeMillis())
            .toJobParameters()

        val list = listOf(
            News.createWithoutAI(
                newsMeta = NewsMeta(
                    newsProvider = NewsProvider.MARKETAUX,
                    newsPublishedTime = LocalDateTime.now(),
                    sourceUrl = "https://example.com",
                ),
                originalContent = Content("title", "content")
            )
        )

        whenever(newsScrapRequester.supports()).thenReturn(NewsProvider.MARKETAUX)
        whenever(newsScrapRequester.scrap(any<LocalDateTime>(), any())).thenReturn(list)
        whenever(newsOpenAiAnalysisRequester.request(any())).thenReturn(
            AiChatResponse(
                analyses = listOf(
                    AiChatResponse.NewsAnalysis(
                        overView = "ai overview",
                        translatedTitle = "ai translated title",
                        translatedContent = "ai translated content",
                        categories = listOf(TargetCategory.BTC),
                        sentimentType = SentimentType.POSITIVE,
                        sentimentRatio = 1.0,
                    )
                )
            )
        )

        // when
        val result = jobLauncherTestUtils.launchJob(params)

        // then
        val foundNewses = newsJpaRepository.findAll()

        assertAll(
            Executable { assertEquals("COMPLETED", result.exitStatus.exitCode) },
            Executable { assertEquals(1, foundNewses.size) },
            Executable { assertEquals("ai overview", foundNewses.get(0).overview) },
        )
    }
}
