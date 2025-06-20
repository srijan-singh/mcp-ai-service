package mcp.ai_service.service.chat;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mcp.ai_service.tool.ToolConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatSessionManager {

    private final ChatClient chatClient;
    private final ChatMemoryManagerFactory memoryFactory;

    public String getAnswer(String question, String userId) {
        validate(question, userId);
        var memoryManager = memoryFactory.getOrCreateManager(userId);
        // Add user message
        memoryManager.addUserMessage(question);
        try {
            // Build the prompt
            var content = chatClient.prompt()
                    .messages(memoryManager.getMessages())
                    .functions(ToolConfig.USER_TOOL)
                    .call()
                    .content();
            // Add assistant message to memory
            memoryManager.addAssistantMessage(content);
            return content;
        // Run time exception can happen at .call()
        } catch (RuntimeException exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }

    private void validate(String question, String userId) {
        if (StringUtil.isNullOrEmpty(userId)) {
            log.error("userId can't be {}", userId);
            throw new IllegalArgumentException("Invalid User ID: Can't be null or empty!");
        }
        if (StringUtil.isNullOrEmpty(question)) {
            log.error("question can't be {}", question);
            throw new IllegalArgumentException("Invalid Question: Can't be null or empty!");
        }
    }
}

