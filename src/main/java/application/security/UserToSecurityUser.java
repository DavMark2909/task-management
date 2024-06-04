package application.security;

import application.entity.Role;
import application.entity.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserToSecurityUser {

    public static SecurityUser from(User user){
        return new SecurityUser(user.getUsername(), user.getPassword(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
    }
}
