package mcp.ai_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class represents the user data from the user-tool-service
 * 
 * @author Srijan Singh
 */
@AllArgsConstructor
@Getter
public class UserData {
    private final String userID;
    private final String userName;
}
