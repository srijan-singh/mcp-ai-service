package mcp.ai_service.tool.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    public static final String USER_SERVICE = "userService";

    @Bean
    public UserService userService() {
        return new UserService();
    }
}
