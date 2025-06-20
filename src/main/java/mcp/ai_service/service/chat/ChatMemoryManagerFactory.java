/* mcp‑ai‑service — AI service for MCP
 * Copyright (C) 2025 Srijan Singh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details:
 *     https://www.gnu.org/licenses/gpl-3.0.txt
 */
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

