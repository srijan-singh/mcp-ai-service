package mcp.ai_service.service;

import lombok.extern.slf4j.Slf4j;
import mcp.ai_service.tool.ToolConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OllamaServiceImplementation implements AIService {

    private ChatClient chatClient;

    @Bean
    CommandLineRunner runner(ChatClient.Builder chatClientBuilder) {
        return args -> this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String askModel(String question) {
        var content = chatClient.prompt()
                .user(question)
                .functions(ToolConfig.USER_TOOL)
                .call()
                .content();
        log.debug(content);
        return content;
    }
}