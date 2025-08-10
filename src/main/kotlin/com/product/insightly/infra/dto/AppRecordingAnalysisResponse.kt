package com.product.insightly.infra.dto

data class ProductAnalysisResponse(
    val strategicSummary: StrategicSummary,
    val recommendedFeatures: List<RecommendedFeature>,
    val scores: Scores,
    val finalSummary: String
) {
    data class StrategicSummary(
        val strength: String,
        val weakness: String,
        val opportunity: String
    )

    data class RecommendedFeature(
        val rank: Int,
        val title: String,
        val description: String
    )

    data class Scores(
        val uxConvenience: ScoreDetail,
        val userBenefit: ScoreDetail,
        val problemSolving: ScoreDetail,
        val totalScore100: Int // 0 ~ 100
    ) {
        data class ScoreDetail(
            val score: Int,
            val rank: Int,
            val evaluation: String
        )
    }

}

