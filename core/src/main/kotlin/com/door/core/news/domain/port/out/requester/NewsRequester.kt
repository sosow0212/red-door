package com.door.core.news.domain.port.out.requester

import com.door.core.global.NewsProvider
import com.door.core.news.domain.News

/**
 * 사전 정의된 API를 이용해 뉴스를 스크랩한다.
 */
interface NewsRequester {

    fun supports(): NewsProvider

    fun scrap(): News
}
