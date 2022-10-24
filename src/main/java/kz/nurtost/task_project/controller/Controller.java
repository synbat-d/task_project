package kz.nurtost.task_project.controller;

import kz.nurtost.task_project.DTO.LoginDTO;
import kz.nurtost.task_project.DTO.OrderDTO;
import kz.nurtost.task_project.DTO.SignUpDTO;
import kz.nurtost.task_project.entity.Role;
import kz.nurtost.task_project.entity.User;
import kz.nurtost.task_project.exception_handling.InfoMessage;
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

import javax.validation.Valid;
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
    public List<OrderDTO> getOrderByProductName (@PathVariable String name) {
        return orderService.getOrderByProductName(name);
    }

    @PostMapping("/orders")
    public ResponseEntity<InfoMessage> saveOrder(@Valid @RequestBody OrderDTO orderDTO) {
        InfoMessage message = new InfoMessage();
        message.setInfo("Order added successfully");
        orderService.saveOrder(orderDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/orders/{name}/{username}")
    public OrderDTO changeOrder (@PathVariable String name, @PathVariable String username,
                                 @Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO order = orderService.updateOrderByProductName(name, username, orderDTO);
        return order;
    }

    @PutMapping("/orders/{name}")
    public OrderDTO changeOrder (@PathVariable String name,
                                 @Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO order = orderService.updateOrderByProductName(name, orderDTO);
        return order;
    }

    @DeleteMapping("/orders/{name}/{username}")
    public ResponseEntity<InfoMessage> deleteOrderByProductName (@PathVariable String name,
                                                            @PathVariable String username) {
        InfoMessage message = new InfoMessage();
        message.setInfo("Order deleted successfully");
        orderService.deleteOrderByProductName(name, username);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/orders/{name}")
    public ResponseEntity<InfoMessage> deleteOrderByProductName (@PathVariable String name) {
        InfoMessage message = new InfoMessage();
        message.setInfo("Order deleted successfully");
        orderService.deleteOrderByProductName(name);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PostMapping("/auth/signin")
    public ResponseEntity<InfoMessage> authenticateUser(@Valid @RequestBody LoginDTO loginDto){
        InfoMessage message = new InfoMessage();
        String incorrect = "Username or password is incorrect";
        String success = "User signed-in successfully!";

        loginDto = Corrections.correct(loginDto);
        if(!userRepository.existsByUserName(loginDto.getUsername())){
            message.setInfo(incorrect);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }

        if(!passwordEncoder.matches(loginDto.getPassword(),
                userRepository.findByUserName(loginDto.getUsername()).get().getPassword())){
            message.setInfo(incorrect);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        message.setInfo(success);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDTO signUpDto){
        InfoMessage message = new InfoMessage();

        signUpDto = Corrections.correct(signUpDto);

        if(userRepository.existsByUserName(signUpDto.getUsername())){
            message.setInfo("Username is already taken!");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if(!signUpDto.getPassword().equals(signUpDto.getPasswordConfirmation())){
            message.setInfo("Password and password confirmation do not match");
            return new ResponseEntity<>(message,
                    HttpStatus.BAD_REQUEST);
        }


        User user = new User();
        user.setUserName(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setName(signUpDto.getName());
        user.setSurname(signUpDto.getSurname());
        user.setAvatarLink(signUpDto.getAvatarLink());
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        Role roles = null;
        if (!(signUpDto.getSecretWordForAdmins()==null)) {
            if (signUpDto.getSecretWordForAdmins().equals("IAMADMIN")){
                roles = roleRepository.findByRoleName("ROLE_ADMIN").get();
            }
            else {
                roles = roleRepository.findByRoleName("ROLE_USER").get();
            }
        }

        else {
            roles = roleRepository.findByRoleName("ROLE_USER").get();
        }
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        message.setInfo("User registered successfully");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }




}
