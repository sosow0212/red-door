package com.door.core.config

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JasyptConfig {

    @Value("\${jasypt.encryptor.password}")
    private lateinit var ENCRYPT_KEY: String

    @Bean
    fun encrypt(): StringEncryptor =
        PooledPBEStringEncryptor().apply {
            val config =
                SimpleStringPBEConfig().apply {
                    password = ENCRYPT_KEY
                    algorithm = "PBEWithMD5AndDES"
                    keyObtentionIterations = 1000
                    poolSize = 1
                    setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
                    stringOutputType = "base64"
                }
            setConfig(config)
        }
}
