package kz.nurtost.task_project.repository;

import kz.nurtost.task_project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findByProductName(String name);

}
