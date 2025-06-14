package mcp.ai_service.tool.service;

import org.springframework.beans.factory.annotation.Value;


public class UserService {

    @Value("${user-service.url}")
    private String url;

    @Value("${user-service.user-info}")
    private String userInfo;

    public String getUserInfoAPI() {
        return "%s/%s".formatted(url, userInfo);
    }
}
