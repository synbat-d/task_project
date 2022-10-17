package kz.nurtost.task_project.service;

import kz.nurtost.task_project.DTO.OrderDTO;
import kz.nurtost.task_project.entity.Order;
import kz.nurtost.task_project.exception_handling.NoSuchOrderException;
import kz.nurtost.task_project.input_corrections.Corrections;
import kz.nurtost.task_project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void saveOrder(OrderDTO orderDTO) {
        orderDTO = Corrections.correct(orderDTO);
        Order neworder = new Order(orderDTO.getProductName(), orderDTO.getQuantity(),
                orderDTO.getDeliveryAdress(), orderDTO.getPhoneNumber());
        neworder.setCreatedAt(new Date(System.currentTimeMillis()));
        orderRepository.save(neworder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for(Order order: orders) {
            OrderDTO orderDTO = new OrderDTO(order.getProductName(),
                    order.getQuantity(), order.getDeliveryAdress(), order.getPhoneNumber());
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }

    @Override
    public OrderDTO getOrderByProductName(String productName) {
        productName = Corrections.correct(productName);
        Order order = orderRepository.findByProductName(productName);

        if(order==null)
            throw new NoSuchOrderException("There is no such order with product name called: '" + productName + "'");
        OrderDTO orderDTO = new OrderDTO(order.getProductName(), order.getQuantity(),
                order.getDeliveryAdress(), order.getPhoneNumber());
        return orderDTO;
    }

    @Override
    public void deleteOrderByProductName(String name) {
        name = Corrections.correct(name);
        Order neededOrder = orderRepository.findByProductName(name);
        if(neededOrder==null)
            throw new NoSuchOrderException("There is no such order with product name called: '" + name + "'");
        long neededId = neededOrder.getId();
        orderRepository.deleteById(neededId);
    }

    @Override
    public OrderDTO updateOrderByProductName(String name, OrderDTO orderDTO) {
        name = Corrections.correct(name);
        orderDTO = Corrections.correct(orderDTO);
        Order order = orderRepository.findByProductName(name);
        if(order==null)
            throw new NoSuchOrderException("There is no such order with product name called: '" + name + "'");
        order.setProductName(orderDTO.getProductName());
        order.setDeliveryAdress(orderDTO.getDeliveryAdress());
        order.setQuantity(orderDTO.getQuantity());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        orderRepository.save(order);
        return orderDTO;
    }
}
