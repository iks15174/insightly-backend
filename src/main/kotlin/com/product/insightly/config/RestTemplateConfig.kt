package com.product.insightly.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        val requestFactory = SimpleClientHttpRequestFactory().apply {
            this.setReadTimeout(20000)
            this.setConnectTimeout(3000)
        }

        restTemplate.setRequestFactory(requestFactory)
        return restTemplate
    }
}