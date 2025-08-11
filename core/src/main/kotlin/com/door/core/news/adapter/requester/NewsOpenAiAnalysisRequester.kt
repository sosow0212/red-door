package com.door.core.news.adapter.requester

import com.door.core.global.AiModel
import com.door.core.news.adapter.requester.properties.OpenAiProperties
import com.door.core.news.domain.port.out.requester.NewsAiRequester
import com.door.core.news.domain.port.out.requester.dto.AiChatRequest
import com.door.core.news.domain.port.out.requester.dto.AiChatResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class NewsOpenAiAnalysisRequester(
    private val webClient: WebClient,
    private val openAiProperties: OpenAiProperties
) : NewsAiRequester {

    override fun supports(): AiModel {
        return AiModel.CHATGPT
    }

    override suspend fun request(aiChatRequest: AiChatRequest): AiChatResponse {
        val prompt = getPrompt(aiChatRequest)

        val responseBody = webClient.post()
            .uri(openAiProperties.baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ${openAiProperties.apiKey}")
            .bodyValue(
                mapOf(
                    "model" to openAiProperties.model,
                    "messages" to listOf(
                        mapOf(
                            "role" to "system",
                            "content" to "You are a professional Korean translator and sentiment analyzer."
                        ),
                        mapOf("role" to "user", "content" to prompt)
                    ),
                    "temperature" to 0
                )
            )
            .retrieve()
            .bodyToMono(Map::class.java)
            .awaitSingle()

        val content =
            (((responseBody["choices"] as? List<*>)
                ?.firstOrNull() as? Map<*, *>)
                ?.get("message") as? Map<*, *>)
                ?.get("content") as? String
                ?: throw RuntimeException("Open AI 응답을 확인해주세요. ${responseBody["choices"]}")

        val analyses: List<AiChatResponse.NewsAnalysis> = mapper.readValue(content)
        return AiChatResponse(analyses)
    }

    private fun getPrompt(aiChatRequest: AiChatRequest): String {
        return """
            You are a Korean translator, summarizer, categorizer, and sentiment analyzer.

            You MUST return the result in **valid JSON** with the exact keys and value rules below.
            Do not include any explanations or extra text, only return JSON.

            Rules for fields for each news item:
            - overView: 1~3 sentence summary in Korean.
            - translatedTitle: Korean translation of the original title.
            - translatedContent: Korean translation of the original content.
            - categories: A list of strings, each must be one of the following exactly: [SPY, QQQ, BTC, AAPL, MSFT, NVDA, GOOGL, META, TSLA, NONE].  
              If no relevant ticker exists, return ["NONE"].
            - sentimentType: One of exactly [POSITIVE, NEUTRAL, NEGATIVE].  
              If sentiment is unclear, return "NEUTRAL".
            - sentimentRatio: A number between 0.0 and 1.0 indicating confidence.

            Input will be a JSON array of news objects in this format:
            [
              { "title": "...", "content": "..." },
              ...
            ]

            Return JSON in this format (array of responses):
            [
              {
                "overView": "...",
                "translatedTitle": "...",
                "translatedContent": "...",
                "categories": ["..."],
                "sentimentType": "POSITIVE | NEUTRAL | NEGATIVE",
                "sentimentRatio": 0.0
              },
              ...
            ]

            Here is the input:
            ${mapper.writeValueAsString(aiChatRequest.newsItems)}
        """.trimIndent()
    }

    companion object {
        private var mapper: ObjectMapper = jacksonObjectMapper()
    }
}
