package org.tyaa.training.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tyaa.training.server.entities.RoleEntity;

/**
 * Репозиторий для работы с ролями пользователей
 * */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
