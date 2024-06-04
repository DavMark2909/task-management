package application.security;

import application.dto.UserRegisterDto;
import application.entity.Role;
import application.entity.User;
import application.exception.NoRoleException;
import application.exception.UserAlreadyExistsException;
import application.repository.RoleRepository;
import application.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(UserToSecurityUser::from).orElseThrow();
    }

    @Transactional
    public SecurityUser createUser(UserRegisterDto dto) throws UserAlreadyExistsException, NoRoleException {
        if (userExists(dto.getUsername()))
            throw new UserAlreadyExistsException();
        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setLastName(dto.getLastname());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (String name : dto.getRoles()){
//            Role role = roleRepository.findByName(name).orElseThrow(() -> new NoRoleException(name));
//            roles.add(role);
            Role role = roleRepository.findByName(name).orElseGet(() -> {
                Role newRole = new Role();
                newRole.setName(name);
                return roleRepository.save(newRole);
            });
            roles.add(role);
        }
        user.setRoles(roles);
        User u = userRepository.save(user);
        return UserToSecurityUser.from(u);
    }

    public void updateUser(UserDetails user) {

    }

    public void deleteUser(String username) {

    }

    public void changePassword(String oldPassword, String newPassword) {

    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
