package com.door.core.news.domain

import com.door.core.news.domain.vo.NewsProvider
import com.door.core.news.domain.vo.TargetCategory
import com.door.core.news.fixture.NewsFixture.Companion.뉴스_생성_블룸버그_비트코인
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NewsTest {

    @Test
    fun `뉴스 제공자가 포함되고, 카테고리가 포함된다면 true를 반환한다`() {
        // given
        val news = 뉴스_생성_블룸버그_비트코인()

        // when
        val result = news.isContainsNewsProviderAndCategories(
            listOf(TargetCategory.BTC),
            listOf(NewsProvider.BLOOMBERG)
        )

        // then
        assertThat(result).isTrue()
    }
}
