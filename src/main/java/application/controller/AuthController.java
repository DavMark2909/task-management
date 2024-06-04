package application.controller;

import application.dto.LoginDto;
import application.dto.UserRegisterDto;
import application.entity.User;
import application.exception.NoRoleException;
import application.exception.UserAlreadyExistsException;
import application.security.SecurityUser;
import application.security.TokenGenerator;
import application.security.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController{

    @Autowired
    UserAuthenticationService userService;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto signupDTO) throws NoRoleException, UserAlreadyExistsException {
        SecurityUser user = userService.createUser(signupDTO);

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.getPassword(), user.getAuthorities());

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto){
        Authentication auth = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword()));

        return ResponseEntity.ok(tokenGenerator.createToken(auth));
    }
}
