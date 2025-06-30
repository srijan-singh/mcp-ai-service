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
import mcp.ai_service.tool.ToolConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Service;

/**
 * Manages the chat session with the AI model provides instance to interact with
 * {@link ChatClient} and manage the chat history with {@link ChatMemoryManager}
 * 
 * @author Srijan Singh
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatSessionManager {

    private final ChatClient chatClient;
    private final ChatMemoryManagerFactory memoryFactory;

    /**
     * Get the answer from the AI model
     * @param question requested by user
     * @param userId {@link UserData#getUserID()}
     * @return answer from the AI model
     */
    public String getAnswer(String question, String userId) {
        validate(question, userId);
        var memoryManager = memoryFactory.getOrCreateManager(userId);
        // Add user message to chat
        memoryManager.addUserMessage(question);
        try {
            // Build the prompt
            var content = chatClient.prompt()
                    .messages(memoryManager.getMessages())
                    .tools(ToolConfig.USER_TOOL)
                    .advisors(new SimpleLoggerAdvisor()) // prompt logs
                    .call()
                    .content();
            // Add assistant message to chat
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

