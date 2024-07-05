package application.service;

import application.entity.Role;
import application.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<String> getAllRoles(){
        return roleRepository.getAllRoles();
    }
}
