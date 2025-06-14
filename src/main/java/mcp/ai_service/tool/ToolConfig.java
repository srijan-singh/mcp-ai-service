package mcp.ai_service.tool;

import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ToolConfig {

    public static final String USER_TOOL = "userTool";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Description("Calls User Service API")
    public Function<UserTool.UserInfoRequest, UserTool.UserInfoResponse> userTool() {
        return new UserTool();
    }
}

