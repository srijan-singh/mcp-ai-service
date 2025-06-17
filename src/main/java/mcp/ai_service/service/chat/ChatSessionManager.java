package mcp.ai_service.service.chat;

import mcp.ai_service.tool.ToolConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatSessionManager {

    private final ChatClient chatClient;
    private final ChatMemoryManagerFactory memoryFactory;

    public ChatSessionManager(ChatClient.Builder chatClientBuilder, ChatMemoryManagerFactory memoryFactory) {
        this.chatClient = chatClientBuilder.build();
        this.memoryFactory = memoryFactory;
    }

    public String getAnswer(String question, String userId) {
        var memoryManager = memoryFactory.getOrCreateManager(userId);

        // Add user message
        memoryManager.addUserMessage(question);

        // Build the prompt
        var content = chatClient.prompt()
                .messages(memoryManager.getMessages())
                .functions(ToolConfig.USER_TOOL)
                .call()
                .content();

        // Add assistant message to memory
        memoryManager.addAssistantMessage(content);

        return content;
    }
}

