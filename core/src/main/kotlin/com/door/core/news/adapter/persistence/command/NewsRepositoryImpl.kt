package com.door.core.news.adapter.persistence.command

import com.door.core.news.domain.News
import com.door.core.news.domain.Newses
import com.door.core.news.domain.port.`in`.NewsQueryRequest
import com.door.core.news.domain.port.out.NewsPersistencePort
import org.springframework.stereotype.Repository

@Repository
class NewsRepositoryImpl(
    private val newsJpaMapper: NewsJpaMapper,
    private val newsJpaRepository: NewsJpaRepository,
) : NewsPersistencePort {

    override fun saveAllNews(newses: List<News>): Newses {
        val entities = newses.map { newsJpaMapper.toEntity(it) }
        val savedNewses = newsJpaRepository.saveAll(entities)
            .map { newsJpaMapper.toDomain(it) }

        return Newses(savedNewses)
    }

    override fun findByOverviewIsNull(): Newses {
        val entities = newsJpaRepository.findByOverviewIsNull()
        val foundNewses = entities.map { newsJpaMapper.toDomain(it) }
        return Newses(foundNewses)
    }

    override fun findAllByFilters(request: NewsQueryRequest): Newses {
        TODO("Not yet implemented")
    }
}
