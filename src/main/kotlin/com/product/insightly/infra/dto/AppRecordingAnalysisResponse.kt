package com.product.insightly.infra.dto

data class ProductAnalysisResponse(
    val strategic_summary: StrategicSummary,
    val recommended_features: List<RecommendedFeature>,
    val scores: Scores,
    val final_summary: String
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
        val ux_convenience: ScoreDetail,
        val user_benefit: ScoreDetail,
        val problem_solving: ScoreDetail,
        val total_score_100: Int // 0 ~ 100
    ) {
        data class ScoreDetail(
            val score: Int,
            val rank: Int,
            val evaluation: String
        )
    }

}

