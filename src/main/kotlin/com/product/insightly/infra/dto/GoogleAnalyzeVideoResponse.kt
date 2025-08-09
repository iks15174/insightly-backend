package com.product.insightly.infra.dto

data class GoogleAnalyzeVideoResponse(
    val candidates: List<Candidate>,
    val usageMetadata: UsageMetadata,
    val modelVersion: String,
    val responseId: String
) {
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