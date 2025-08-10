package com.product.insightly.controller

import com.product.insightly.controller.dto.AppRecordingAnalysisStartRequest
import com.product.insightly.infra.dto.ProductAnalysisResponse
import com.product.insightly.service.AppRecordingAnalysisService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api-web/v1/insightly/app-recording-analysis")
class AppRecordingAnalysisController(
    private val appRecordingAnalysisService: AppRecordingAnalysisService,
) {

    @PostMapping("/start")
    fun start(
        @RequestBody request: AppRecordingAnalysisStartRequest
    ): ResponseEntity<List<ProductAnalysisResponse>> {
        return appRecordingAnalysisService.analysis(request.fileNames)
            .let { ResponseEntity.ok(it) }
    }

    /**
     * 영상 분석 상태 조회
     */
    @GetMapping("/status")
    fun getStatus() {

    }
}