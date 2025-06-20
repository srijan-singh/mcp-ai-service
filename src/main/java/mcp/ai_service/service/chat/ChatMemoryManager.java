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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages the chat history via {@link ChatMemory} and keeps track of the
 * message count and conversation id
 * 
 * @author Srijan Singh
 */
@Slf4j
@Getter
@AllArgsConstructor
public class ChatMemoryManager {

    private final AtomicInteger messageCount;
    private final String conversationId;
    private final ChatMemory chatMemory;

    /**
     * Set the system prompt for the model at initialization
     * @param context prompt to set preface
     */
    public void addSystemMessage(String context) {
        verifyMessage(context);
        chatMemory.add(conversationId, new SystemMessage(context));
        incrementMessageCount();
        logMessageCount("Context has been set as %s".formatted(context));
    }

    /**
     * Add user message
     * @param userMessage request by user
     */
    public void addUserMessage(String userMessage) {
        verifyMessage(userMessage);
        chatMemory.add(conversationId, new UserMessage(userMessage));
        incrementMessageCount();
        logMessageCount("User: %s".formatted(userMessage));
    }

    /**
     * Add AI/Assistant message
     * @param assistantMessage response by AI
     */
    public void addAssistantMessage(String assistantMessage) {
        verifyMessage(assistantMessage);
        chatMemory.add(conversationId, new AssistantMessage(assistantMessage));
        incrementMessageCount();
        logMessageCount("Assistant Message: %s".formatted(assistantMessage));
    }

    /**
     * Get all messages
     * @return messages with prompt, userMessage and assistantMessage
     */
    public List<Message> getMessages() {
        return chatMemory.get(conversationId, messageCount.get());
    }

    private void verifyMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or blank!");
        }
    }

    private void incrementMessageCount() {
        messageCount.incrementAndGet(); // or getAndIncrement() depending on behavior
    }

    private void logMessageCount(String context) {
        log.debug("Message Count: {}\t{}", messageCount.get(), context);
    }

}
