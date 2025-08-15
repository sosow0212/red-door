package com.door.batch.news.scrap.tasklet

import com.door.core.news.domain.News
import com.door.core.news.domain.port.out.NewsPersistencePort
import com.door.core.news.domain.port.out.requester.NewsScrapRequester
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
@StepScope
class NewsCrawlingTasklet(
    private val newsScrapRequesters: Set<NewsScrapRequester>,
    private val newsPersistencePort: NewsPersistencePort
) : Tasklet {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("#{jobParameters['publishTimeAfter']}")
    private lateinit var publishTimeAfterParam: String

    @Value("#{jobParameters['limit']}")
    private var limitParam: Int = 3

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        return try {
            val publishTimeAfter = parsePublishTimeAfter()
            val newses = scrapAll(publishTimeAfter, limitParam)
            newsPersistencePort.saveAllNews(newses)

            log.info("총 ${newses.size}개의 뉴스 저장 완료 (기준 시간: $publishTimeAfter, limit: $limitParam)")
            RepeatStatus.FINISHED
        } catch (e: Exception) {
            log.error("뉴스 수집 중 오류 발생", e)
            throw e
        }
    }

    private fun scrapAll(publishTimeAfter: LocalDateTime, limit: Int): List<News> {
        return newsScrapRequesters.flatMap { requester ->
            val provider = requester.supports()

            try {
                log.info("[${provider.name}] 뉴스 수집 시작 - 기준 시간: $publishTimeAfter, limit: $limit")

                runBlocking {
                    requester.scrap(publishTimeAfter, limit)
                        .also { newses ->
                            log.info("[${provider.name}] ${newses.size}개의 뉴스 수집 완료")
                        }
                }
            } catch (e: Exception) {
                log.error("[${provider.name}] 뉴스 수집 실패", e)
                emptyList()
            }
        }
    }

    private fun parsePublishTimeAfter(): LocalDateTime {
        return try {
            LocalDateTime.parse(publishTimeAfterParam, dateFormatter)
        } catch (e: Exception) {
            log.warn("잘못된 publishTimeAfter 파라미터 형식: $publishTimeAfterParam. 기본값(1일 전)으로 설정합니다.")
            LocalDateTime.now().minusDays(1)
        }
    }

    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    }
}
