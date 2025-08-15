package com.door.batch.news.scrap.job

import com.door.batch.news.scrap.tasklet.OpenAiAnalysisTasklet
import com.door.batch.news.scrap.tasklet.NewsCrawlingTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

/**
 * 1. 뉴스 스크랩
 * 2. 뉴스 ai 분석
 * 3. 가공된 뉴스 데이터 저장
 */
@Configuration
class NewsScrapJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val newsCrawlingTasklet: NewsCrawlingTasklet,
    private val openAiAnalysisTasklet: OpenAiAnalysisTasklet
) {

    @Bean
    fun newsScrapJob(): Job {
        return JobBuilder("newsScrapJob", jobRepository)
            .start(newsCrawlingStep())
            .next(aiAnalysisStep())
            .build()
    }

    // Step 1 - API에서 뉴스 데이터 수집
    @Bean
    fun newsCrawlingStep(): Step {
        return StepBuilder("newsCrawlingStep", jobRepository)
            .tasklet(newsCrawlingTasklet, transactionManager)
            .build()
    }

    // Step 2 - AI 분석
    @Bean
    fun aiAnalysisStep(): Step {
        return StepBuilder("aiAnalysisStep", jobRepository)
            .tasklet(openAiAnalysisTasklet, transactionManager)
            .build()
    }
}
