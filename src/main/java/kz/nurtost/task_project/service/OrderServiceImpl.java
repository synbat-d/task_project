package kz.nurtost.task_project.service;

import kz.nurtost.task_project.DTO.OrderDTO;
import kz.nurtost.task_project.DTO.OrderDTOforAdmin;
import kz.nurtost.task_project.entity.Order;
import kz.nurtost.task_project.entity.Role;
import kz.nurtost.task_project.entity.User;
import kz.nurtost.task_project.exception_handling.NoSuchOrderException;
import kz.nurtost.task_project.input_corrections.Corrections;
import kz.nurtost.task_project.repository.OrderRepository;
import kz.nurtost.task_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    Object principal = null;

    public String getUserNameOfCurrentlyLoggedInUser() {
        String username=null;
        principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        }
        else {
            username = principal.toString();
        }
        return username;
    }

    public String getUserRoleOfCurrentlyLoggedInUser() {
        String username = getUserNameOfCurrentlyLoggedInUser();
        User currentUser = userRepository.findByUserName(username).get();
        Set<Role> roles = currentUser.getRoles();
        Role role = roles.iterator().next();
        String roleName = role.getRoleName();
        return roleName;

    }

    @Override
    public void saveOrder(OrderDTO orderDTO) {
        String username = getUserNameOfCurrentlyLoggedInUser();
        String userRole = getUserRoleOfCurrentlyLoggedInUser();
        orderDTO = Corrections.correct(orderDTO);
        Order neworder = new Order(orderDTO.getProductName(), orderDTO.getQuantity(),
                orderDTO.getDeliveryAdress(), orderDTO.getPhoneNumber(), username, userRole);
        neworder.setCreatedAt(new Date(System.currentTimeMillis()));
        orderRepository.save(neworder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        String roleOfCurrentlyLoggedInUser = getUserRoleOfCurrentlyLoggedInUser();
        String usernameOfCurrentlyLoggedInUser = getUserNameOfCurrentlyLoggedInUser();
        if (roleOfCurrentlyLoggedInUser.equals("ROLE_ADMIN")) {
            List<Order> orders = orderRepository.findAll();
            List<OrderDTO> orderDTOs = new ArrayList<>();
            for(Order order: orders) {
                OrderDTOforAdmin orderDTO = new OrderDTOforAdmin(order.getProductName(),
                        order.getQuantity(), order.getDeliveryAdress(),
                        order.getPhoneNumber(),order.getUserName(),
                        order.getUserRole());
                orderDTOs.add(orderDTO);
            }
            return orderDTOs;
        }
        else {
            List<Order> orders = orderRepository.findOrdersByUserName(usernameOfCurrentlyLoggedInUser);
            List<OrderDTO> orderDTOs = new ArrayList<>();
            for(Order order: orders) {
                OrderDTO orderDTO = new OrderDTO(order.getProductName(),
                        order.getQuantity(), order.getDeliveryAdress(), order.getPhoneNumber());
                orderDTOs.add(orderDTO);
            }
            return orderDTOs;
        }

    }

    @Override
    public List<OrderDTO> getOrderByProductName(String productName) {
        productName = Corrections.correct(productName);
        String roleName = getUserRoleOfCurrentlyLoggedInUser();
        String userName = getUserNameOfCurrentlyLoggedInUser();
        if (roleName.equals("ROLE_ADMIN")) {
            List<Order> orders = orderRepository.findOrdersByProductName(productName);
            if(orders.isEmpty())
                throw new NoSuchOrderException("There is no such order with product name called: '" + productName + "'");
            List<OrderDTO> orderDTOs = new ArrayList<>();
            for (Order order: orders) {
                orderDTOs.add(new OrderDTOforAdmin(order.getProductName(), order.getQuantity(),
                        order.getDeliveryAdress(), order.getPhoneNumber(),
                        order.getUserName(), order.getUserRole()));
            }
            return orderDTOs;
//            OrderDTOforAdmin orderDTO = new OrderDTOforAdmin(order.getProductName(), order.getQuantity(),
//                    order.getDeliveryAdress(), order.getPhoneNumber(), order.getUserName(), order.getUserRole());
//            return orderDTO;
        }
        else {
            Order order = orderRepository.findOrderByProductNameAndUserName(productName, userName);
            if(order==null)
                throw new NoSuchOrderException("There is no such order with product name called: '" + productName + "'");
            OrderDTO orderDTO = new OrderDTO(order.getProductName(), order.getQuantity(),
                    order.getDeliveryAdress(), order.getPhoneNumber());
            List<OrderDTO> orderDTOs = new ArrayList<>();
            orderDTOs.add(orderDTO);
            return orderDTOs;
        }
    }

    @Override
    public void deleteOrderByProductName(String name, String urlUsername) {
        name = Corrections.correct(name);
        String roleName = getUserRoleOfCurrentlyLoggedInUser();
        String userName = getUserNameOfCurrentlyLoggedInUser();
        Order neededOrder = null;
        if (roleName.equals("ROLE_ADMIN")) {
            neededOrder = orderRepository.findOrderByProductNameAndUserName(name, urlUsername);
        }
        else {
            neededOrder = orderRepository.findOrderByProductNameAndUserName(name, userName);
        }

        if(neededOrder==null)
            throw new NoSuchOrderException("There is no such order with product name called: '" + name
                    + "' and username '" + urlUsername + "'");
        long neededId = neededOrder.getId();
        orderRepository.deleteById(neededId);
    }

    @Override
    public void deleteOrderByProductName(String name) {
        name = Corrections.correct(name);
        String roleName = getUserRoleOfCurrentlyLoggedInUser();
        String userName = getUserNameOfCurrentlyLoggedInUser();
        Order neededOrder = null;
        if (roleName.equals("ROLE_ADMIN")) {
            throw new NoSuchOrderException("You should indicate username in the url after /"+ name +"/<username>" );
        }
        else {
            neededOrder = orderRepository.findOrderByProductNameAndUserName(name, userName);
        }

        if(neededOrder==null)
            throw new NoSuchOrderException("There is no such order with product name called: '" + name + "'");
        long neededId = neededOrder.getId();
        orderRepository.deleteById(neededId);
    }

    @Override
    public OrderDTO updateOrderByProductName(String name, String urlUsername, OrderDTO orderDTO) {
        name = Corrections.correct(name);
        orderDTO = Corrections.correct(orderDTO);
        String roleName = getUserRoleOfCurrentlyLoggedInUser();
        String userName = getUserNameOfCurrentlyLoggedInUser();
        Order order = null;
        if (roleName.equals("ROLE_ADMIN")) {
            order = orderRepository.findOrderByProductNameAndUserName(name, urlUsername);
        }
        else {
            order = orderRepository.findOrderByProductNameAndUserName(name, userName);
        }
        if(order==null)
            throw new NoSuchOrderException("There is no such order with product name called: '" + name + "'");
        order.setProductName(orderDTO.getProductName());
        order.setDeliveryAdress(orderDTO.getDeliveryAdress());
        order.setQuantity(orderDTO.getQuantity());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        orderRepository.save(order);
        return orderDTO;
    }

    @Override
    public OrderDTO updateOrderByProductName(String name, OrderDTO orderDTO) {
        name = Corrections.correct(name);
        orderDTO = Corrections.correct(orderDTO);
        String roleName = getUserRoleOfCurrentlyLoggedInUser();
        String userName = getUserNameOfCurrentlyLoggedInUser();
        Order order = null;
        if (roleName.equals("ROLE_ADMIN")) {
            throw new NoSuchOrderException("You should indicate username in the url after /"+ name +"/<username>" );
        }
        else {
            order = orderRepository.findOrderByProductNameAndUserName(name, userName);
        }
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
