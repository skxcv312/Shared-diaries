package org.zerock.study.domain.diaryGenerate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import java.util.concurrent.Executors;

@Configuration
public class OpenAIConfig {
    @Bean
    public OpenAIClient openAIOkHttpClient(@Value("${openAI.api-key}") String apiKey) {
        return OpenAIOkHttpClient.builder()
                .apiKey(apiKey) // api 키 설정
                .streamHandlerExecutor(Executors.newFixedThreadPool(4)) // 쓰레드풀 설정
                .build();
    }

}
