package org.zerock.study.domain.diaryGenerate.service;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.completions.CompletionUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class DiaryGenerateService {
    private final OpenAIClient client;

    // gpt 파라미터 생성
    ChatCompletionCreateParams OpenAIParameter(String userMessage) {
        String developerMessage = String.join(
                "입력으로 사용자의 일기 내용을 받는다. ",
                "일기의 문장 구조를 자연스럽게 다듬고 부족한 부분은 보완하여 완성된 글로 출력한다. ",
                "일기 내용 외의 정보는 포함하지 않는다. ",
                "응답은 순수한 String 형식의 텍스트로만 출력한다."
        );

        return ChatCompletionCreateParams.builder()
                .addDeveloperMessage(developerMessage)
                .addUserMessage(userMessage)
                .model(ChatModel.GPT_4_1_MINI)
                .build();
    }

    // 일기 자동 완성
    public String completeDiaryIntegration(String userDiary) {
        ChatCompletionCreateParams params = OpenAIParameter(userDiary);
        ChatCompletion chatCompletion = client.chat().completions().create(params);

        String responseText = chatCompletion.choices()
                .get(0)
                .message()
                .content()
                .orElse("요약에 실패했습니다.");

        Long totalTokens = chatCompletion.usage()
                .map(CompletionUsage::totalTokens)
                .orElse(0L);

        log.info("GPT 응답 내용: {}", responseText);
        log.info("사용한 토큰 수: {}", totalTokens);

        return responseText;
    }


}
