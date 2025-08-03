package com.door.core.news.domain.vo

enum class NewsProvider {

    ALL,
    BLOOMBERG;

    companion object {
        fun getAllProviders(): List<NewsProvider> {
            return entries
        }
    }
}
