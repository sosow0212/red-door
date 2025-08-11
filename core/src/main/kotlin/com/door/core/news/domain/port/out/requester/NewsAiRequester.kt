package com.door.core.news.domain.port.out.requester

import com.door.core.global.AiModel
import com.door.core.news.domain.port.out.requester.dto.AiChatRequest
import com.door.core.news.domain.port.out.requester.dto.AiChatResponse

/**
 * AI Chatbot을 통해 뉴스 분석, 요약, 긍정지수 평가
 */
interface NewsAiRequester {

    fun supports(): AiModel

    suspend fun request(aiChatRequest: AiChatRequest): AiChatResponse
}

