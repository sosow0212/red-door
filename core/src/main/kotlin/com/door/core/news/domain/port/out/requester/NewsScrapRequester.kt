package com.door.core.news.domain.port.out.requester

import com.door.core.global.NewsProvider
import com.door.core.news.domain.News
import java.time.LocalDateTime

/**
 * 사전 정의된 API를 이용해 뉴스를 스크랩한다.
 */
interface NewsScrapRequester {

    fun supports(): NewsProvider

    suspend fun scrap(
        publishTimeAfter: LocalDateTime,
        limit: Int,
    ): List<News>
}
