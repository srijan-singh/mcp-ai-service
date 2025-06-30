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

    public record UserInfoRequest (String userId) {}
    public record UserInfoResponse (UserData userData) {}

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Tool(name = "Get User Info", description = "Fetch user info from user-tool-service by userId")
    public UserInfoResponse apply(UserInfoRequest userInfoRequest) {
        String url = UriComponentsBuilder.fromUriString(userService.getUserInfoAPI())
                .queryParam("userId", userInfoRequest.userId)
                .build()
                .toUriString();
        log.debug("Calling URL: %s".formatted(url));
        var response = restTemplate.getForObject(url, UserData.class);
        log.debug("Response Received: %s".formatted(response));
        return new UserInfoResponse(response);
    }
}
