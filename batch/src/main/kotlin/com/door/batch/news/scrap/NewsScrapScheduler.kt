package com.door.batch.news.scrap

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@ConditionalOnProperty(
    name = ["scheduling.news-scrap.enabled"],
    havingValue = "true",
    matchIfMissing = true
)
@EnableScheduling
@Component
class NewsScrapScheduler(
    private val jobLauncher: JobLauncher,

    @Qualifier("newsScrapJob")
    private val newsScrapJob: Job
) {

    @Scheduled(fixedRate = EVERY_JOB_INTERVAL_MINUTE)
    fun runNewsScrapJob() {
        val jobParameters = JobParametersBuilder()
            .addString("publishTimeAfter", createSeoulLocationTime())
            .addLong("limit", SCRAP_LIMIT_SIZE)
            .toJobParameters()

        jobLauncher.run(newsScrapJob, jobParameters)
    }

    private fun createSeoulLocationTime() = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        .minusMinutes(JOB_INTERVAL_MINUTE)
        .format(DATE_TIME_FORMATTER)
        .toString()

    companion object {
        private const val JOB_INTERVAL_MINUTE: Long = 15
        private const val EVERY_JOB_INTERVAL_MINUTE: Long = JOB_INTERVAL_MINUTE * 60 * 1000
        private const val SCRAP_LIMIT_SIZE: Long = 3
        private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    }
}
