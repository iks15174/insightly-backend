package com.product.insightly.infra

import com.product.insightly.infra.dto.GoogleAnalyzeVideoRequest
import com.product.insightly.infra.dto.GoogleAnalyzeVideoResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.io.File
import java.util.*

@Component
class GoogleAIClient(
    private val restTemplate: RestTemplate,
    @Value("\${google.ai.api.key}") private val apiKey: String,
) {
    private val modelName = "gemini-2.5-flash:generateContent"
    private val apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/${modelName}"
    private val googleApiKeyHeaderName = "x-goog-api-key"

    fun analyzeVideo(file: File): GoogleAnalyzeVideoResponse? {
        val videoBytes = file.readBytes()
        val base64Video = Base64.getEncoder().encodeToString(videoBytes)

        val request = GoogleAnalyzeVideoRequest.createRequest(base64Video)

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("x-goog-api-key", apiKey)
        }

        val entity = HttpEntity(request, headers)

        val response = restTemplate.exchange(
            /* url = */ apiUrl,
            /* method = */ HttpMethod.POST,
            /* requestEntity = */ entity,
            /* responseType = */ GoogleAnalyzeVideoResponse::class.java
        )

        return response.body
    }


}