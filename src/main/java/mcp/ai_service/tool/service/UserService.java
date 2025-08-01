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
