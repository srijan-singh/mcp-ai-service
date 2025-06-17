package mcp.ai_service.service.chat;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ChatMemoryManagerFactory {

    private final Map<String, ChatMemoryManager> memoryMap = new ConcurrentHashMap<>();
    private final ChatMemory chatMemory;

    @Value("${chat.context.user-info}")
    private String systemPrompt;

    public ChatMemoryManagerFactory() {
        this.chatMemory = new InMemoryChatMemory();
    }

    public ChatMemoryManager getOrCreateManager(String userId) {
        return memoryMap.computeIfAbsent(userId, id -> {
            var manager = new ChatMemoryManager(new AtomicInteger(0), id, chatMemory);
            manager.addSystemMessage(systemPrompt);
            return manager;
        });
    }
}

