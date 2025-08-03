package com.door.core.news.domain.port.out.requester.dto

import com.door.core.news.domain.vo.SentimentType
import com.door.core.news.domain.vo.TargetCategory

/**
 * TODO : 챗봇의 json 응답 전문을 필드로 변환 필요
 */
data class AiChatResponse(
    val overView: String,
    val translatedTitle: String,
    val translatedContent: String,
    val categories: List<TargetCategory>,
    val sentimentType: SentimentType,
    val sentimentRatio: Double,
) {
}
