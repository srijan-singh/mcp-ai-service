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
package mcp.ai_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mcp.ai_service.pojo.UserData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mcp.ai_service.service.AIService;

/**
 * Controller for the AI service
 * 
 * @see AIService
 * 
 * @author Srijan Singh
 */
@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    /**
     * Get the answer from the AI model
     * @param question requested by user
     * @param userId {@link UserData#getUserID()}
     * @return answer from the AI model
     */
    @RequestMapping("/ask")
    public String ask(@RequestParam String question, @RequestParam String userId) {
        log.debug("Question: {} from userId {}", question, userId);
        return aiService.askModel(question, userId);
    }
}
