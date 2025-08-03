package com.door.core.news.domain.vo

import com.door.core.news.fixture.NewsFixture.Companion.인공지능_오버뷰_생성_비트코인_긍정적_30프로
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AiOverviewTest {

    @Test
    fun `현재 Category가 포함되어 있으면 true를 반환한다`() {
        // given
        val aiOverview = 인공지능_오버뷰_생성_비트코인_긍정적_30프로()

        // when
        val result = aiOverview.isMatchedCategory(listOf(TargetCategory.BTC, TargetCategory.TSLA))

        // then
        assertThat(result).isTrue()
    }
}
