package com.door.core.news.domain.port.out

import com.door.core.global.AiModel
import com.door.core.news.domain.News
import com.door.core.news.domain.vo.Content

/**
 * API를 통해 스크랩한 뉴스를 요약하고 전망을 분석해준다
 */
interface NewsAiAnalysisRequesterPort {

    fun analyseNewses(
        activeAiModel: AiModel,
        originalNewsContent: Content
    ): List<News>
}
