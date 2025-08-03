package com.door.core.news.domain.port.`in`

import com.door.core.news.domain.Newses

interface NewsCommandUseCase {

    /**
     * (1) 뉴스 크롤링
     * (2) AI 챗봇 이용
     * (3) 저장
     */
    fun scrapNewses(): Newses
}
