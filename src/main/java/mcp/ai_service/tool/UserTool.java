package mcp.ai_service.tool;

import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import mcp.ai_service.pojo.UserData;
import mcp.ai_service.tool.service.UserService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public class UserTool implements Function<UserTool.UserInfoRequest, UserTool.UserInfoResponse> {

    public record UserInfoRequest (String userName) {}
    public record UserInfoResponse (UserData userData) {}

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Tool(name = "Get User Info", description = "Fetch user info from user-tool-service by userName")
    public UserInfoResponse apply(UserInfoRequest userInfoRequest) {
        String url = UriComponentsBuilder.fromUriString(userService.getUserInfoAPI())
                .queryParam("userName", userInfoRequest.userName)
                .build()
                .toUriString();
        log.debug("Calling URL: %s".formatted(url));
        var response = restTemplate.getForObject(url, UserData.class);
        log.debug("Response Received: %s".formatted(response));
        return new UserInfoResponse(response);
    }
}
