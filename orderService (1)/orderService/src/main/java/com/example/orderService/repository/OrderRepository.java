package  com.example.orderService.repository;

import com.example.orderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}