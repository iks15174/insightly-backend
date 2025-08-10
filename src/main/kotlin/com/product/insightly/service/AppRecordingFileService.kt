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
    @Value("\${app.video.upload-dir}")
    private val uploadDir: String,
    @Value("\${app.video.scene-dir}")
    private val sceneDir: String,
    private val videoFileSceneService: VideoFileSceneService,
) {

    private val logger = LoggerFactory.getLogger(AppRecordingFileService::class.java)

    fun upload(file: MultipartFile): AppRecordingFileUploadDto {
        validateVideoFile(file)

        val uploadPath = createDirectory(uploadDir)
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

    fun uploadOnlyEssential(file: MultipartFile) {
        validateVideoFile(file)

        val uploadedPath = createDirectory(uploadDir)
        val scenePath = createDirectory("${sceneDir}\\${generateUniqueKey()}")

        val uploadedFilename = generateUniqueFilename(file.originalFilename ?: "video")
        val extractedFileName = generateUniqueFilename("extracted-${file.originalFilename}")

        val uploadedFilePath = uploadedPath.resolve(uploadedFilename)
        val extractedFilePath = uploadedPath.resolve(extractedFileName)

        Files.copy(file.inputStream, uploadedFilePath, StandardCopyOption.REPLACE_EXISTING)
        videoFileSceneService.extractEssentialScene(uploadedFilePath, scenePath)
        videoFileSceneService.combineScenesToVideo(scenePath, extractedFilePath)

        deleteFile(uploadedFilePath)
        deleteDirectory(scenePath)
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
    private fun createDirectory(path: String): Path {
        val uploadPath = Paths.get(path)
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
            logger.info("디렉토리 생성: {}", uploadPath)
        }
        return uploadPath
    }

    /**
     * 고유한 파일명 생성
     */
    private fun generateUniqueFilename(originalFilename: String): String {
        val extension = getFileExtension(originalFilename)
        val baseFilename = originalFilename.substringBeforeLast(".")

        return "${generateUniqueKey()}_${baseFilename}.${extension}"
    }

    private fun generateUniqueKey(): String {
        val timestamp = System.currentTimeMillis()
        val uuid = UUID.randomUUID().toString().substring(0, 8)
        return "${timestamp}_${uuid}"
    }

    fun deleteDirectory(dir: Path) {
        if (Files.exists(dir)) {
            // 하위 모든 파일/디렉토리 탐색 → 역순 정렬 → 삭제
            Files.walk(dir)
                .sorted(Comparator.reverseOrder())
                .forEach { Files.deleteIfExists(it) }
        }
    }

    fun deleteFile(path: Path) {
        Files.deleteIfExists(path)
    }

    /**
     * 파일 확장자 추출
     */
    private fun getFileExtension(filename: String): String {
        return filename.substringAfterLast(".", "").lowercase()
    }
}