package ru.zaikin.transaction.transaction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaikin.transaction.transaction.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
