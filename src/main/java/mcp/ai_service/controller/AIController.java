package mcp.ai_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mcp.ai_service.service.AIService;

@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @RequestMapping("/ask")
    public String ask(@RequestParam String question, @RequestParam String userId) {
        log.debug("Question: {} from userId {}", question, userId);
        return aiService.askModel(question, userId);
    }
}
