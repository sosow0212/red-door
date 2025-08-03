package com.door.core.news.domain.port.out

import com.door.core.news.domain.Newses
import com.door.core.news.domain.port.`in`.NewsQueryRequest

interface NewsPersistencePort {

    fun saveAllNews(newses: Newses) : Newses

    fun findAllByFilters(request: NewsQueryRequest): Newses
}
