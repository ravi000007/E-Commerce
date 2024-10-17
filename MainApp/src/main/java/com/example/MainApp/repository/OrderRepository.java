package com.example.MainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MainApp.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
