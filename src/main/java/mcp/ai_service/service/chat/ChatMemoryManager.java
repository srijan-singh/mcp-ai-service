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

@Slf4j
@Getter
@AllArgsConstructor
public class ChatMemoryManager {

    private final AtomicInteger messageCount;
    private final String conversationId;
    private final ChatMemory chatMemory;

    public void addSystemMessage(String context) {
        verifyMessage(context);
        chatMemory.add(conversationId, new SystemMessage(context));
        incrementMessageCount();
        logMessageCount("Context has been set as %s".formatted(context));
    }

    public void addUserMessage(String userMessage) {
        verifyMessage(userMessage);
        chatMemory.add(conversationId, new UserMessage(userMessage));
        incrementMessageCount();
        logMessageCount("User: %s".formatted(userMessage));
    }

    public void addAssistantMessage(String assistantMessage) {
        verifyMessage(assistantMessage);
        chatMemory.add(conversationId, new AssistantMessage(assistantMessage));
        incrementMessageCount();
        logMessageCount("Assistant Message: %s".formatted(assistantMessage));
    }

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
