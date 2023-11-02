package org.tyaa.training.server.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель роли пользователя
 * */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel {
    /**
     * Локально уникальный идентификатор
     * */
    public Long id;
    /**
     * Название роли
     * */
    public String name;
}
