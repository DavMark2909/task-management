package application.service;

import application.dto.user.UserDto;
import application.dto.user.UserToDto;
import application.entity.User;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserDto getUserByUsername(String username){
        return UserToDto.from(userRepository.findByUsername(username).orElseThrow());
    }

    public User getRealUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow();
    }

    public List<User> getUserByRole(String role){
        return userRepository.findUsersByRoleName(role);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getUserByFullName(String name, String lastname){
        return userRepository.findByNameAndLastName(name, lastname).orElseThrow();
    }
}
