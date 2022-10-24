package kz.nurtost.task_project.repository;

import kz.nurtost.task_project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findByProductName(String name);
    public List<Order> findOrdersByProductName(String productName);
    public Order findOrderByProductNameAndUserName(String productName, String username);

    public List<Order> findOrdersByUserName(String username);

}
