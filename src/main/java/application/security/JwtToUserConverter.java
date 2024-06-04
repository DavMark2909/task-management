package application.security;

import application.entity.User;
import application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private final UserRepository repository;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        String username = jwt.getSubject();
        log.info("Username retrieves is {}", username);
        User user = repository.findByUsername(username).orElseThrow();
        SecurityUser securityUser = UserToSecurityUser.from(user);
        return new UsernamePasswordAuthenticationToken(securityUser, jwt, securityUser.getAuthorities());
    }
}
