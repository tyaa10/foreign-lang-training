package org.tyaa.training.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tyaa.training.server.entities.UserEntity;

/**
 * Репозиторий для работы с пользователями
 * */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByName(String name);
}
