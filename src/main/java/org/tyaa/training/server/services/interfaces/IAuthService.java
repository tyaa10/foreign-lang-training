package org.tyaa.training.server.services.interfaces;

import org.springframework.security.core.Authentication;
import org.tyaa.training.server.models.ResponseModel;
import org.tyaa.training.server.models.RoleModel;
import org.tyaa.training.server.models.UserModel;

import java.util.List;

/**
 * Интерфейс службы аутентификации
 * */
public interface IAuthService {
    /**
     * Метод получения списка всех ролей
     * */
    ResponseModel getRoles();
    ResponseModel createRole(RoleModel roleModel);
    ResponseModel getRoleUsers(Long roleId);
    ResponseModel createUser(UserModel userModel);
    ResponseModel deleteUser(Long id);
    ResponseModel makeUserAdmin(Long id) throws Exception;
    ResponseModel check(Authentication authentication);
    ResponseModel onSignOut();
    ResponseModel onError();
}
