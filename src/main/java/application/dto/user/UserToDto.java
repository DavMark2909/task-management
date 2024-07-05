package application.dto.user;

import application.entity.User;
import org.springframework.http.HttpStatus;

import java.util.stream.Collectors;

public class UserToDto {

    public static UserDto from(User user){
        return UserDto.builder().username(user.getUsername()).name(user.getName())
                .lastname(user.getLastName()).roles(user.getRoles().stream().map(role -> role.getName()).
                        collect(Collectors.toList())).status(HttpStatus.OK).build();
    }
}
