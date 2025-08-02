package com.product.insightly.controller

import com.product.insightly.controller.dto.AppRecordingAnalysisStartRequest
import com.product.insightly.service.AppRecordingAnalysisService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api-web/v1/insightly/app-recording-analysis")
class AppRecordingAnalysisController(
    private val appRecordingAnalysisService: AppRecordingAnalysisService,
) {

    @PostMapping("/start")
    fun start(
        @RequestBody request: AppRecordingAnalysisStartRequest
    ) {
        return appRecordingAnalysisService.analysis(request.fileNames)
    }
}