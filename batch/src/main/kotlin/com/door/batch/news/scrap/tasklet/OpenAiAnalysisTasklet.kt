package com.door.batch.news.scrap.tasklet

import com.door.core.news.adapter.requester.overview.NewsOpenAiAnalysisRequester
import com.door.core.news.domain.News
import com.door.core.news.domain.port.out.NewsPersistencePort
import com.door.core.news.domain.port.out.requester.dto.AiChatRequest
import com.door.core.news.domain.port.out.requester.dto.AiChatResponse
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
@StepScope
class OpenAiAnalysisTasklet(
    private val newsPersistencePort: NewsPersistencePort,
    private val newsOpenAiAnalysisRequester: NewsOpenAiAnalysisRequester
) : Tasklet {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        return try {
            val unAnalyzedNewses = newsPersistencePort.findByOverviewIsNull().newses

            if (unAnalyzedNewses.isNotEmpty()) {
                log.info("AI 분석을 진행합니다.")
                processNewsWithAi(unAnalyzedNewses)
            } else {
                log.info("AI 분석할 새로운 뉴스가 없습니다")
            }

            RepeatStatus.FINISHED
        } catch (e: Exception) {
            log.error("AI 분석 중 오류 발생", e)
            throw e
        }
    }

    fun processNewsWithAi(unAnalyzedNewses: List<News>) {
        try {
            val analysisResults = runBlocking {
                newsOpenAiAnalysisRequester.request(createAiRequest(unAnalyzedNewses))
            }

            val updatedNewses = updateNewsWithResults(unAnalyzedNewses, analysisResults)
            newsPersistencePort.saveAllNews(updatedNewses)
        } catch (e: Exception) {
            log.error("벌크 분석 실패: ${e.message}")
            throw e
        }
    }

    private fun createAiRequest(newses: List<News>): AiChatRequest {
        return AiChatRequest(
            newses.map { news ->
                AiChatRequest.NewsItemRequest(
                    title = news.originalContent.title,
                    content = news.originalContent.content
                )
            }
        )
    }

    fun updateNewsWithResults(newses: List<News>, response: AiChatResponse): List<News> {
        return newses.zip(response.analyses)
            .map { (news, analysis) ->
                news.updateAiAnalysis(
                    overview = analysis.overView,
                    translatedTitle = analysis.translatedTitle,
                    translatedContent = analysis.translatedContent,
                    categories = analysis.categories,
                    sentimentType = analysis.sentimentType,
                    sentimentRatio = analysis.sentimentRatio
                )
            }
    }
}
