package com.door.core.news.domain

import com.door.core.global.NewsProvider
import com.door.core.news.domain.vo.TargetCategory
import com.door.core.news.fixture.NewsFixture.Companion.뉴스_생성_블룸버그_비트코인
import com.door.core.news.fixture.NewsFixture.Companion.뉴스_생성_블룸버그_테슬라
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import kotlin.test.assertEquals

class NewsesTest {

    @Test
    fun `리스트에서 필터에 해당되는 뉴스를 조회한다`() {
        // given
        val targetNews = 뉴스_생성_블룸버그_테슬라()

        val newses = Newses(
            listOf(
                targetNews,
                뉴스_생성_블룸버그_비트코인(),
            )
        )

        // when
        val result = newses.findAllNewsesFilteredOfCategoriesAndProviders(
            listOf(TargetCategory.TSLA),
            listOf(NewsProvider.ALL)
        )

        // then
        assertAll(
            Executable { assertEquals(result.size, 1) },
            Executable { assertTrue(result.contains(targetNews)) },
        )
    }
}
