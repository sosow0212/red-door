package com.door.core.news.adapter.requester

import com.door.core.global.NewsProvider
import com.door.core.news.adapter.requester.properties.MarketAuxProperties
import com.door.core.news.domain.News
import com.door.core.news.domain.port.out.requester.NewsScrapRequester
import com.door.core.news.domain.vo.Content
import com.door.core.news.domain.vo.NewsMeta
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class MarketAuxNewsScrapRequester(
    private val webClient: WebClient,
    private val marketAuxProperties: MarketAuxProperties
) : NewsScrapRequester {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun supports(): NewsProvider {
        return NewsProvider.MARKETAUX
    }

    override suspend fun scrap(
        publishTimeAfter: LocalDateTime,
        limit: Int
    ): List<News> {
        val formattedPublishedTimeAfter = publishTimeAfter.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))

        return try {
            val response = webClient.get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path(marketAuxProperties.baseUrl)
                        .queryParam("countries", "us")
                        .queryParam("group", "economic")
                        .queryParam("limit", limit)
                        .queryParam("published_after", formattedPublishedTimeAfter)
                        .queryParam("api_token", marketAuxProperties.apiKey)
                        .build()
                }
                .retrieve()
                .bodyToMono<MarketAuxResponse>()
                .awaitSingle()

            response.data.map { newsItem ->
                News(
                    id = 0L,
                    NewsMeta.of(
                        newsProvider = NewsProvider.MARKETAUX,
                        newsPublishedTime = newsItem.published_at,
                        sourceUrl = newsItem.url
                    ),
                    scrapedTime = LocalDateTime.now(),
                    originalContent = Content(
                        title = newsItem.title,
                        content = newsItem.description
                    ),
                    translatedContent = null,
                    aiOverView = null
                )
            }
        } catch (e: WebClientResponseException) {
            log.warn("Failed to fetch news from Marketaux API: ${e.statusCode} - ${e.responseBodyAsString}")
            emptyList()
        } catch (e: Exception) {
            log.error("Unexpected error during Marketaux API call", e)
            emptyList()
        }
    }

    data class MarketAuxResponse(
        val warnings: List<String>?,
        val meta: Meta,
        val data: List<NewsItem>
    )

    data class Meta(
        val found: Int,
        val returned: Int,
        val limit: Int,
        val page: Int
    )

    data class NewsItem(
        val uuid: String,
        val title: String,
        val description: String,
        val keywords: String?,
        val snippet: String,
        val url: String,
        val image_url: String,
        val language: String,
        val published_at: String,
        val source: String,
        val relevance_score: Double?,
        val entities: List<Entity>,
        val similar: List<Any>
    )

    data class Entity(
        val symbol: String?,
        val name: String?,
        val exchange: String?,
        val exchange_long: String?,
        val country: String?,
        val type: String?,
        val industry: String?,
        val match_score: Double?,
        val sentiment_score: Double?,
        val highlights: List<Highlight>
    )

    data class Highlight(
        val highlight: String,
        val sentiment: Double,
        val highlighted_in: String
    )
}

