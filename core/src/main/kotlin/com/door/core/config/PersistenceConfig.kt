package com.door.core.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@ComponentScan(basePackages = ["com.door.core"])
@EntityScan(basePackages = ["com.door.core"])
@EnableJpaRepositories(basePackages = ["com.door.core"])
class PersistenceConfig
