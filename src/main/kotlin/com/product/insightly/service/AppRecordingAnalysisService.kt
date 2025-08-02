package com.product.insightly.service

import com.product.insightly.infra.GoogleAIClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

@Service
class AppRecordingAnalysisService(
    @Value("\${app.video.upload-dir:/uploads/videos}")
    private val uploadDir: String,
    private val googleAIClient: GoogleAIClient,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun analysis(files: List<String>) {
        val results = files.mapNotNull { fileName ->
            val fileInDisk = File("${uploadDir}/${fileName}")
            if (fileInDisk.exists().not()) {
                logger.info("file 이 존재하지 않습니다 (file: ${fileName})")
                return@mapNotNull null
            }
            googleAIClient.analyzeVideo(fileInDisk)
        }
        println(results)
    }
}