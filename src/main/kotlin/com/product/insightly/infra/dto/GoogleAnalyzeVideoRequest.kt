package com.product.insightly.infra.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.product.insightly.domain.AppRecordingAnalysisPrompt

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GoogleAnalyzeVideoRequest(
    val contents: List<Content>
) {
    data class Content(
        val parts: List<Part>,
    ) {
        sealed class Part {

            data class DataPart(
                val inlineData: InlineData,
            ) : Part() {
                data class InlineData(
                    val mimeType: String,
                    val data: String,
                )
            }

            data class CommandPart(
                val text: String
            ) : Part()
        }
    }

    companion object {
        fun createRequest(base64Video: String) = GoogleAnalyzeVideoRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Content.Part.DataPart(
                            inlineData = Content.Part.DataPart.InlineData(
                                mimeType = "video/mp4",
                                data = base64Video,
                            ),
                        ),
                        Content.Part.CommandPart(
                            text = AppRecordingAnalysisPrompt.text
                        )
                    )
                )
            )
        )
    }
}