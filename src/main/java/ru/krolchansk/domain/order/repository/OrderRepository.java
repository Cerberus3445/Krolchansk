package ru.krolchansk.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krolchansk.domain.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
