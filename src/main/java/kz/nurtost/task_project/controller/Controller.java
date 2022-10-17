package kz.nurtost.task_project.controller;

import kz.nurtost.task_project.DTO.LoginDTO;
import kz.nurtost.task_project.DTO.OrderDTO;
import kz.nurtost.task_project.DTO.SignUpDTO;
import kz.nurtost.task_project.entity.Order;
import kz.nurtost.task_project.entity.Role;
import kz.nurtost.task_project.entity.User;
import kz.nurtost.task_project.input_corrections.Corrections;
import kz.nurtost.task_project.repository.OrderRepository;
import kz.nurtost.task_project.repository.RoleRepository;
import kz.nurtost.task_project.repository.UserRepository;
import kz.nurtost.task_project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/orders")
    public List<OrderDTO> getAllOrders () {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{name}")
    public OrderDTO getOrderByProductName (@PathVariable String name) {
        return orderService.getOrderByProductName(name);
    }

    @PostMapping("/orders")
    public ResponseEntity<String> saveOrder(@RequestBody OrderDTO orderDTO) {
        orderService.saveOrder(orderDTO);
        return new ResponseEntity<>("Order added successfully", HttpStatus.OK);
    }

    @PutMapping("/orders/{name}")
    public OrderDTO changeOrder (@PathVariable String name, @RequestBody OrderDTO orderDTO) {
        OrderDTO order = orderService.updateOrderByProductName(name, orderDTO);
        return order;
    }

    @DeleteMapping("/orders/{name}")
    public ResponseEntity<String> deleteOrderByProductName (@PathVariable String name) {
        orderService.deleteOrderByProductName(name);
        return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto){
        loginDto = Corrections.correct(loginDto);
        if(!userRepository.existsByUserName(loginDto.getUsername())){
            return new ResponseEntity<>("Username or password is incorrect", HttpStatus.UNAUTHORIZED);
        }

        if(!passwordEncoder.matches(loginDto.getPassword(),
                userRepository.findByUserName(loginDto.getUsername()).get().getPassword())){
            return new ResponseEntity<>("Username or password is incorrect", HttpStatus.UNAUTHORIZED);
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto){

        signUpDto = Corrections.correct(signUpDto);

        if(userRepository.existsByUserName(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(!signUpDto.getPassword().equals(signUpDto.getPasswordConfirmation())){
            return new ResponseEntity<>("Password and password confirmation do not match",
                    HttpStatus.BAD_REQUEST);
        }


        User user = new User();
        user.setUserName(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setName(signUpDto.getName());
        user.setSurname(signUpDto.getSurname());
        user.setAvatarLink(signUpDto.getAvatarLink());
        user.setCreatedAt(new Date(System.currentTimeMillis()));

        Role roles = roleRepository.findByRoleName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }




}
