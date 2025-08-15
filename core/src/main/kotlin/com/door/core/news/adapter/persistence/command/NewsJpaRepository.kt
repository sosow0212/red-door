package com.door.core.news.adapter.persistence.command

import org.springframework.data.jpa.repository.JpaRepository

interface NewsJpaRepository : JpaRepository<NewsJpaEntity, Long> {

    fun findByOverviewIsNull(): List<NewsJpaEntity>
}
