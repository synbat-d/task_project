package kz.nurtost.task_project.input_corrections;

import kz.nurtost.task_project.DTO.LoginDTO;
import kz.nurtost.task_project.DTO.OrderDTO;
import kz.nurtost.task_project.DTO.SignUpDTO;

public class Corrections {
    public static OrderDTO correct(OrderDTO orderDTO) {
        return new OrderDTO(orderDTO.getProductName().trim().toLowerCase(),
                orderDTO.getQuantity(),
                orderDTO.getDeliveryAdress().trim().toLowerCase(),
                orderDTO.getPhoneNumber().trim().toLowerCase());
    }

    public static String correct(String word) {
        return word.trim().toLowerCase();
    }

    public static SignUpDTO correct(SignUpDTO signUpDTO) {
        return new SignUpDTO(signUpDTO.getUsername().trim().toLowerCase(),
                signUpDTO.getPassword(),
                signUpDTO.getPasswordConfirmation(),
                signUpDTO.getName().trim().toLowerCase(),
                signUpDTO.getSurname().trim().toLowerCase(),
                signUpDTO.getAvatarLink().trim());
    }

    public static LoginDTO correct(LoginDTO loginDTO) {
        return new LoginDTO(loginDTO.getUsername().trim().toLowerCase(), loginDTO.getPassword());
    }

}
