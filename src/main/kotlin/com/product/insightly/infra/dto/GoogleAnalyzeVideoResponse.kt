package com.product.insightly.infra.dto

/**
 * API 응답이 json```[json string]``` 으로 오기 때문에 사용
 */

private val regex = Regex("""^```json\s*|\s*```$""", RegexOption.MULTILINE)

// TODO: 응답 문서 보고 Nullable  수정
data class GoogleAnalyzeVideoResponse(
    val candidates: List<Candidate>,
    val usageMetadata: UsageMetadata,
    val modelVersion: String,
    val responseId: String
) {
    val analyzedAnswer = candidates[0].content.parts[0].text.replace(regex, "")

    data class Candidate(
        val content: Content,
        val finishReason: String,
        val avgLogprobs: Double
    ) {
        data class Content(
            val parts: List<Part>,
            val role: String
        ) {

            data class Part(
                val text: String
            )
        }
    }

    data class UsageMetadata(
        val promptTokenCount: Int,
        val candidatesTokenCount: Int,
        val totalTokenCount: Int,
        val promptTokensDetails: List<ModalityTokenDetail> = emptyList(),
        val candidatesTokensDetails: List<ModalityTokenDetail> = emptyList(),
    ) {
        data class ModalityTokenDetail(
            val modality: String,
            val tokenCount: Int
        )

    }
}