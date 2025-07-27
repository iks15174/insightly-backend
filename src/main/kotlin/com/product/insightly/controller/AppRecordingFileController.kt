package com.product.insightly.controller

import com.product.insightly.service.AppRecordingFileService
import com.product.insightly.service.dto.AppRecordingFileUploadDto
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api-web/v1/insightly/app-recording-file")
class AppRecordingFileController(
    private val appRecordingFileService: AppRecordingFileService,
) {

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestParam("file") file: MultipartFile): ResponseEntity<AppRecordingFileUploadDto> {
        return appRecordingFileService.upload(file).let {
            ResponseEntity.ok(it)
        }
    }
}