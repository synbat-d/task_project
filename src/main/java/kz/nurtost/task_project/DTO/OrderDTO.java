package kz.nurtost.task_project.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OrderDTO {
    @NotBlank(message = "Product Name should not be empty")
    @Size(min = 3, message = "Product Name should contain at least 3 characters")
    private String productName;
    @Min(value = 1, message = "Quantity should be minimum 1")
    private int quantity;
    @NotBlank(message = "Delivery Adress should not be empty")
    private String deliveryAdress;

    @Pattern(regexp = "\\d{4}-\\d{3}-\\d{2}-\\d{2}", message = "please use XXXX-XXX-XX-XX pattern")
    private String phoneNumber;

    public OrderDTO() {
    }

    public OrderDTO(String productName, int quantity, String deliveryAdress, String phoneNumber) {
        this.productName = productName;
        this.quantity = quantity;
        this.deliveryAdress = deliveryAdress;
        this.phoneNumber = phoneNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryAdress() {
        return deliveryAdress;
    }

    public void setDeliveryAdress(String deliveryAdress) {
        this.deliveryAdress = deliveryAdress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
