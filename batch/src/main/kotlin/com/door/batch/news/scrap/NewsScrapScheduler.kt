package com.door.batch.news.scrap

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@EnableScheduling
@Component
class NewsScrapScheduler(
    private val jobLauncher: JobLauncher,

    @Qualifier("newsScrapJob")
    private val newsScrapJob: Job
) {

    @Scheduled(fixedRate = EVERY_FIFTY_MINUTE)
    fun runNewsScrapJob() {
        val jobParameters = JobParametersBuilder()
            .addString("publishTimeAfter", LocalDateTime.now().minusHours(1).toString())
            .addLong("limit", SCRAP_LIMIT_SIZE)
            .toJobParameters()

        jobLauncher.run(newsScrapJob, jobParameters)
    }

    companion object {
        private const val EVERY_FIFTY_MINUTE: Long = 15 * 60 * 1000
        private const val SCRAP_LIMIT_SIZE: Long = 5
    }
}
