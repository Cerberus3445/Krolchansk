package ru.krolchansk.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krolchansk.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
