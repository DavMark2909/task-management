package application.dto.auth;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
