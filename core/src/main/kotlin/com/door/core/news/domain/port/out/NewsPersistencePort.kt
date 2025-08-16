package com.door.core.news.domain.port.out

import com.door.core.news.domain.News
import com.door.core.news.domain.Newses
import com.door.core.news.domain.port.`in`.NewsQueryRequest

interface NewsPersistencePort {

    fun saveAllNews(newses: List<News>): Newses

    fun findByOverviewIsNull(): Newses

    fun findAllByFilters(request: NewsQueryRequest): Newses
}
