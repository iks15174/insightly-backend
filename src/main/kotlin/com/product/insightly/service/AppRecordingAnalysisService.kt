package com.product.insightly.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.product.insightly.infra.GoogleAIClient
import com.product.insightly.infra.dto.ProductAnalysisResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

@Service
class AppRecordingAnalysisService(
    @Value("\${app.video.upload-dir:/uploads/videos}")
    private val uploadDir: String,
    private val googleAIClient: GoogleAIClient,
    private val objectMapper: ObjectMapper,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun analysis(files: List<String>): List<ProductAnalysisResponse> {
        val responses = files.mapNotNull { fileName ->
            val fileInDisk = File("${uploadDir}/${fileName}")
            if (fileInDisk.exists().not()) {
                logger.info("file 이 존재하지 않습니다 (file: ${fileName})")
                return@mapNotNull null
            }
            googleAIClient.analyzeVideo(fileInDisk)
        }

        val results = responses
            .map { objectMapper.readValue(it.analyzedAnswer, ProductAnalysisResponse::class.java) }
            .also { println(it) }
        return results
    }
}