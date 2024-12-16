package ru.zaikin.transaction.transaction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaikin.transaction.transaction.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
