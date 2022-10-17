package kz.nurtost.task_project.DTO;

public class OrderDTO {
    private String productName;
    private int quantity;
    private String deliveryAdress;
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
