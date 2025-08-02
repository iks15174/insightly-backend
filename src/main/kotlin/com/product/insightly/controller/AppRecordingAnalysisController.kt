package com.product.insightly.controller

import com.product.insightly.controller.dto.AppRecordingAnalysisStartRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api-web/v1/insightly/app-recording-analysis")
class AppRecordingAnalysisController {

    @PostMapping("/start")
    fun start(
        @RequestBody request: AppRecordingAnalysisStartRequest
    ) {
        TODO()
    }
}