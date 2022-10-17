package kz.nurtost.task_project.service;

import kz.nurtost.task_project.DTO.OrderDTO;
import kz.nurtost.task_project.entity.Order;

import java.util.List;

public interface OrderService {
    public void saveOrder(OrderDTO orderDTO);
    public List<OrderDTO> getAllOrders();
    public OrderDTO getOrderByProductName(String productName);
    public void deleteOrderByProductName(String name);

    public OrderDTO updateOrderByProductName(String name, OrderDTO orderDTO);
}
