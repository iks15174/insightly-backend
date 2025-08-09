package com.product.insightly.infra.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

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
                            text = "이 영상은 프로덕트 영상이야. 프로덕트를 내 앱 입장에서 전략적 총평 (강점, 약점, 기회), 추천 기능 1~3순위, 5점 만점 프로덕트 점수 (UX편리함, 사용자 혜택, 실질적 문제 해결)로 점수를 매겨서 순위도 평가해주고 설명도 각각 써줘. 합산은 100점 만점으로 환산해서 자연수로 점수 평가해주고, 총평도 써줘.이걸 읽는 사람은 PO, 디자이너들이라는 걸 감안하고, 전략적 총평은 각각 20자 이내로, 순위에서 평가는 30자를 넘기지 말아줘. 추천 기능은 제목은 10자 이내, 설명은 20자 이내로 써줘.기존에 알고 있는 지식은 전혀 쓰지말고 영상만 보고 프로덕트적으로 유추하고 분석해줘.응답 형식은 JSON으로 해줘"
                        )
                    )
                )
            )
        )
    }
}