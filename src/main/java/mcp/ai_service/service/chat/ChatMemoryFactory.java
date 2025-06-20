package mcp.ai_service.service.chat;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Component;

/**
 * Factory for creating ChatMemory instances
 * 
 * @author Srijan Singh
 */
@Component
public class ChatMemoryFactory {

    public ChatMemory create() {
        return new InMemoryChatMemory();
    }
}
