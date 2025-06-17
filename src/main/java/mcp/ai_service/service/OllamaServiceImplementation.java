package mcp.ai_service.service;

import lombok.extern.slf4j.Slf4j;
import mcp.ai_service.service.chat.ChatSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OllamaServiceImplementation implements AIService {

    private static final String DEFAULT_USER_ID = "default-user";

    @Autowired
    private ChatSessionManager sessionManager;

    @Override
    public String askModel(String question) {
        String response = sessionManager.getAnswer(question, DEFAULT_USER_ID);
        log.debug("Model response: {}", response);
        return response;
    }
}
