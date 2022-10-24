package kz.nurtost.task_project.DTO;

public class OrderDTOforAdmin extends OrderDTO{
    private String ordered_by_username;
    private String ordered_by_user_role;

    public OrderDTOforAdmin() {
    }

    public OrderDTOforAdmin(String productName, int quantity,
                            String deliveryAdress, String phoneNumber,
                            String ordered_by_username,
                            String ordered_by_user_role) {
        super(productName, quantity, deliveryAdress, phoneNumber);
        this.ordered_by_username = ordered_by_username;
        this.ordered_by_user_role = ordered_by_user_role;
    }

    public String getOrdered_by_username() {
        return ordered_by_username;
    }

    public void setOrdered_by_username(String ordered_by_username) {
        this.ordered_by_username = ordered_by_username;
    }

    public String getOrdered_by_user_role() {
        return ordered_by_user_role;
    }

    public void setOrdered_by_user_role(String ordered_by_user_role) {
        this.ordered_by_user_role = ordered_by_user_role;
    }
}
