package mcp.ai_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mcp.ai_service.service.chat.ChatSessionManager;
import org.springframework.stereotype.Service;

/**
 * Service for interacting with Ollama models via {@link ChatSessionManager}
 * 
 * @author Srijan Singh
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaServiceImplementation implements AIService {

    private final ChatSessionManager sessionManager;

    @Override
    public String askModel(String question, String userId) {
        String response = sessionManager.getAnswer(question, userId);
        log.debug("Model response: {}", response);
        return response;
    }
}
