package org.tyaa.training.server.services;

import org.springframework.stereotype.Service;
import org.tyaa.training.server.models.RoleModel;
import org.tyaa.training.server.repositories.RoleRepository;
import org.tyaa.training.server.services.interfaces.IAuthService;

import java.util.List;

/**
 * Реализация службы аутентификации, использующая РБД-репозитории
 * */
@Service
public class AuthService implements IAuthService {

    private final RoleRepository roleRepository;

    public AuthService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleModel> getRoles() {
        return roleRepository.findAll().stream()
                .map(roleEntity -> RoleModel.builder().id(roleEntity.getId()).name(roleEntity.getName()).build())
                .toList();
    }
}
