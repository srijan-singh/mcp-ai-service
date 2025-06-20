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
package mcp.ai_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mcp.ai_service.service.chat.ChatSessionManager;
import org.springframework.stereotype.Service;

/**
 * Service for interacting with Ollama models via {@link ChatSessionManager}
 * 
 * @author Srijan Singh
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaServiceImplementation implements AIService {

    private final ChatSessionManager sessionManager;

    @Override
    public String askModel(String question, String userId) {
        String response = sessionManager.getAnswer(question, userId);
        log.debug("Model response: {}", response);
        return response;
    }
}
