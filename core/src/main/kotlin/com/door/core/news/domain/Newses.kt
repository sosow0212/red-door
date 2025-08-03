package com.door.core.news.domain

import com.door.core.global.NewsProvider
import com.door.core.news.domain.vo.TargetCategory

data class Newses(
    val newses: List<News>,
) {

    fun findAllNewsesFilteredOfCategoriesAndProviders(
        categories: List<TargetCategory>,
        providers: List<NewsProvider>,
    ): List<News> {
        val filteredProviders = getFilteredProviders(providers)

        return newses.filter { it.isContainsNewsProviderAndCategories(categories, filteredProviders) }
            .toList()
    }

    private fun getFilteredProviders(providers: List<NewsProvider>): List<NewsProvider> {
        var filteredProviders: List<NewsProvider> = ArrayList(providers)

        if (providers.isEmpty() || providers.contains(NewsProvider.ALL)) {
            filteredProviders = ArrayList(NewsProvider.getAllProviders())
        }

        return filteredProviders
    }
}
