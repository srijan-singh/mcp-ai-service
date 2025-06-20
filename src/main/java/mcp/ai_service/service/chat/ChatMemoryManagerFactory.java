package mcp.ai_service.service.chat;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMemoryManagerFactory {

    private final Map<String, ChatMemoryManager> memoryMap = new ConcurrentHashMap<>();
    private final ChatMemoryFactory chatMemoryFactory;

    @Value("${chat.context.user-info}")
    private String systemPrompt;

    public ChatMemoryManager getOrCreateManager(String userId) {
        validate(userId);
        return memoryMap.computeIfAbsent(userId, id -> {
            var manager = new ChatMemoryManager(new AtomicInteger(0), id, chatMemoryFactory.create());
            manager.addSystemMessage(systemPrompt);
            return manager;
        });
    }

    private void validate(String userId) {
        if (StringUtil.isNullOrEmpty(userId)) {
            log.error("userId can't be {}", userId);
            throw new IllegalArgumentException("Invalid User ID: Can't be null or empty!");
        }
        if (StringUtil.isNullOrEmpty(systemPrompt)) {
            log.error("systemPrompt can't be {}", systemPrompt);
            throw new IllegalArgumentException("Invalid Prompt: Can't be null or empty!");
        }
    }
}

