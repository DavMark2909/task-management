package application.controller;

import application.dto.user.UserDto;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/load")
    public ResponseEntity<UserDto> getMain(Authentication auth){
        return ResponseEntity.ok(userService.getUserByUsername(auth.getName()));
    }


}
