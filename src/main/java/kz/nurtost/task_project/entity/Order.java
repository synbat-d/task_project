package kz.nurtost.task_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "orders")
public class Order extends Db_abstract_entity{

    @Column(name = "product_name", unique = true)
    @NotBlank(message = "Product Name should not be empty")
    @Size(min = 3, message = "Product Name should contain at least 3 characters")
    private String productName;

    @Column(name = "quantity")
    @Min(value = 1)
    private int quantity;

    @Column(name = "delivery_adress")
    @NotBlank(message = "Delivery Adress should not be empty")
    private String deliveryAdress;

    @Column (name = "phone_number")
    @Pattern(regexp = "\\d{4}-\\d{3}-\\d{2}-\\d{2}", message = "please use XXXX-XXX-XX-XX pattern")
    private String phoneNumber;

    public Order() {
    }

    public Order(String productName, int quantity, String deliveryAdress, String phoneNumber) {
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
