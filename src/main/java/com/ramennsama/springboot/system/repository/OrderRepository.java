package com.ramennsama.springboot.system.repository;

import com.ramennsama.springboot.system.entity.Order;
import com.ramennsama.springboot.system.entity.enumtype.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(OrderStatus status);
    Optional<Order> findByVnpTxnRef(String vnpTxnRef);
}
