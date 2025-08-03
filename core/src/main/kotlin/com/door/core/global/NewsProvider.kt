package com.door.core.global

enum class NewsProvider {

    ALL,
    BLOOMBERG;

    companion object {
        fun getAllProviders(): List<NewsProvider> {
            return entries
        }
    }
}
