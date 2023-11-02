package org.tyaa.training.server.services.interfaces;

import org.tyaa.training.server.models.RoleModel;

import java.util.List;

/**
 * Интерфейс службы аутентификации
 * */
public interface IAuthService {
    /**
     * Метод получения списка всех ролей
     * */
    List<RoleModel> getRoles();
}
