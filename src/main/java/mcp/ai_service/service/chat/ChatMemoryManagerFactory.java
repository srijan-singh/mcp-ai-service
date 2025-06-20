package mcp.ai_service.service.chat;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mcp.ai_service.pojo.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Factory for creating {@link ChatMemoryManager} instances and link it with {@link UserData#getUserID()}
 * 
 * @author Srijan Singh
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMemoryManagerFactory {

    private final Map<String, ChatMemoryManager> memoryMap = new ConcurrentHashMap<>();
    private final ChatMemoryFactory chatMemoryFactory;

    /**
     * Prompt to set preface for the model
     * @see ChatMemoryManager#addSystemMessage(String)
     */
    @Value("${chat.context.user-info}")
    private String systemPrompt;

    /**
     * Gets/Create a ChatMemoryManager instance and set prompt {@link ChatMemoryManager#addSystemMessage(String)}
     * @param userId {@link UserData#getUserID()}
     * @return ChatMemoryManager
     */
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

