package com.product.insightly.domain

object AppRecordingAnalysisPrompt {
    val text = """
        이 영상은 프로덕트 영상이야. 
        [분석 지시사항]  
        - 프로덕트를 내 앱 입장에서 **전략적 총평(강점, 약점, 기회)**: 각 20자 이내로 작성  
        - **추천 기능 1~3순위**: 제목(10자 이내), 설명(20자 이내)  
        - **프로덕트 점수**: UX 편리함, 사용자 혜택, 실질적 문제 해결 → 각 항목 5점 만점 평가 및 순위(평가 설명은 30자 이내)  
        - 세 항목 점수를 합산해 100점 만점으로 환산(자연수)  
        - 최종 총평 작성 (자유 길이, 1~3문장)  
        - 기존에 알고 있는 지식은 전혀 쓰지 말고, **영상만 보고 유추 및 분석**  

        [응답 형식]
        - 아래 형태의 json string 만을 응답해
        {
          "strategicSummary": {
            "strength": "string",
            "weakness": "string",
            "opportunity": "string"
          },
          "recommendedFeatures": [
            {
              "rank": 1,
              "title": "string",
              "description": "string"
            }
          ],
          "scores": {
            "uxConvenience": {
              "score": "integer(0~5)",
              "rank": "integer(1~3)",
              "evaluation": "string"
            },
            "userBenefit": {
              "score": "integer(0~5)",
              "rank": "integer(1~3)",
              "evaluation": "string"
            },
            "problemSolving": {
              "score": "integer(0~5)",
              "rank": "integer(1~3)",
              "evaluation": "string"
            },
            "totalScore100": "integer(0~100)"
          },
          "finalSummary": "string(자유 길이)"
        }

    """.trimIndent()
}