package application.dto.auth;

import lombok.Data;

import java.util.List;

@Data
public class UserRegisterDto {
    private String name;
    private String lastname;
    private String username;
    private String password;
    private List<String> roles;
}
