package com.door.core.news.domain.vo

data class AiOverview(
    // AI 분석 내용
    val overview: String,

    // 감정 분석
    val sentimentType: SentimentType,
    val sentimentScore: Double,

    // 주요 영향 대상
    val targetCategories: List<TargetCategory>
) {

    fun isMatchedCategory(givenCategories: List<TargetCategory>): Boolean {
        return targetCategories.any { it in givenCategories }
    }
}
