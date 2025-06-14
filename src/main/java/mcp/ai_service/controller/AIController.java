package mcp.ai_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mcp.ai_service.service.AIService;

@Slf4j
@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @RequestMapping("/ask")
    public String ask(@RequestParam String question) {
        log.debug("Question %s".formatted(question));
        return aiService.askModel(question);
    }
}
