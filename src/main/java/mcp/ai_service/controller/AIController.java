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
