package com.door.core.global

enum class NewsProvider {

    ALL,
    BLOOMBERG,
    MARKETAUX;

    companion object {
        fun getAllProviders(): List<NewsProvider> {
            return entries
        }
    }
}
