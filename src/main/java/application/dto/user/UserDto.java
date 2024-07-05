package application.dto.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
public class UserDto {
    String name, lastname, username;
    List<String> roles;
    HttpStatus status;
}
