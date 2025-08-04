package com.door.batch.news.job.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.PlatformTransactionManager

//@Configuration
//@EnableScheduling
class NewsScrapJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val jobLauncher: JobLauncher
) {

    @Scheduled(fixedRate = FIFTY_MINUTE)
    fun runNewsJob() {
        val jobParameters = JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters()

        jobLauncher.run(newsScrapJob(), jobParameters)
    }

    @Bean
    fun newsScrapJob(): Job {
        return JobBuilder("newsScrapJob", jobRepository)
            .start(newsCollectionStep())
            .next(aiAnalysisStep())
            .next(dataSaveStep())
            .build()
    }

    // Step 1 - API에서 뉴스 데이터 수집
    @Bean
    fun newsCollectionStep(): Step {
        return StepBuilder("newsCollectionStep", jobRepository)
            .chunk<Void, String>(MAXIMUM_CRAWLING_DATA_CHUNK_SIZE, transactionManager)
            .reader(TODO())
            .processor(TODO())
            .writer(TODO())
            .build()
    }

    // Step 2 - AI 분석
    @Bean
    fun aiAnalysisStep(): Step {
        return StepBuilder("aiAnalysisStep", jobRepository)
            .chunk<String, String>(MAXIMUM_CRAWLING_DATA_CHUNK_SIZE, transactionManager)
            .reader(TODO())
            .processor(TODO())
            .writer(TODO())
            .build()
    }

    // Step 3: 최종 저장
    @Bean
    fun dataSaveStep(): Step {
        return StepBuilder("dataSaveStep", jobRepository)
            .chunk<String, String>(MAXIMUM_CRAWLING_DATA_CHUNK_SIZE, transactionManager)
            .reader(TODO())
            .writer(TODO())
            .build()
    }

    companion object {
        private const val FIFTY_MINUTE: Long = 15 * 60 * 1000
        private const val MAXIMUM_CRAWLING_DATA_CHUNK_SIZE: Int = 30
    }
}
