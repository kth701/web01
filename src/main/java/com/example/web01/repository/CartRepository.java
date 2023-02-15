package com.example.web01.repository;

import com.example.web01.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // 개발자 직접 작성
}
