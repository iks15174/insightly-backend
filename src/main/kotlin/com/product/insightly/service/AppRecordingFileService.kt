package com.product.insightly.service

import com.product.insightly.service.dto.AppRecordingFileUploadDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class AppRecordingFileService(
    @Value("\${app.video.upload-dir:/uploads/videos}")
    private val uploadDir: String
) {

    private val logger = LoggerFactory.getLogger(AppRecordingFileService::class.java)

    fun upload(file: MultipartFile): AppRecordingFileUploadDto {
        validateVideoFile(file)

        val uploadPath = createUploadDirectory()
        val uniqueFilename = generateUniqueFilename(file.originalFilename ?: "video")
        val targetPath = uploadPath.resolve(uniqueFilename)

        // 파일 저장
        Files.copy(file.inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING)

        logger.info("영상 파일 저장 완료: {}", targetPath)

        return AppRecordingFileUploadDto(
            filename = uniqueFilename,
            size = file.size,
        )
    }

    /**
     * 영상 파일 유효성 검사
     */
    private fun validateVideoFile(file: MultipartFile) {
        if (file.isEmpty) {
            throw IllegalArgumentException("파일이 비어있습니다.")
        }

        val allowedExtensions = setOf("mp4")
        val fileExtension = getFileExtension(file.originalFilename ?: "")

        if (fileExtension !in allowedExtensions) {
            throw IllegalArgumentException(
                "지원하지 않는 파일 형식입니다. 지원 형식: ${allowedExtensions.joinToString(", ")}"
            )
        }
    }

    /**
     * 업로드 디렉토리 생성
     */
    private fun createUploadDirectory(): Path {
        val uploadPath = Paths.get(uploadDir)
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
            logger.info("업로드 디렉토리 생성: {}", uploadPath)
        }
        return uploadPath
    }

    /**
     * 고유한 파일명 생성
     */
    private fun generateUniqueFilename(originalFilename: String): String {
        val timestamp = System.currentTimeMillis()
        val uuid = UUID.randomUUID().toString().substring(0, 8)
        val extension = getFileExtension(originalFilename)
        val baseFilename = originalFilename.substringBeforeLast(".")

        return "${timestamp}_${uuid}_${baseFilename}.${extension}"
    }

    /**
     * 파일 확장자 추출
     */
    private fun getFileExtension(filename: String): String {
        return filename.substringAfterLast(".", "").lowercase()
    }
}