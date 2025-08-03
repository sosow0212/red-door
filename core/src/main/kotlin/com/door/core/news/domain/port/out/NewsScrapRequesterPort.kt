package com.door.core.news.domain.port.out

import com.door.core.global.NewsProvider
import com.door.core.news.domain.News

/**
 * API를 통해 최신 뉴스를 스크랩 해온다.
 */
interface NewsScrapRequesterPort {

    fun scrap(newsProvider: NewsProvider): List<News>
}
