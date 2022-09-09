package assignment.shop.order.dto;

import assignment.shop.order.Address;
import assignment.shop.order.Order;
import lombok.Data;

@Data
public class GetOrdersResDto {
    private Long orderId;
    private String itemName;
    private int itemPrice;
    private Address address;
    private int orderPrice;
    private int orderCount;

    public GetOrdersResDto(Order order) {
        orderId = order.getId();
        itemName = order.getOrderItems().get(0).getItem().getName();
        itemPrice = order.getOrderItems().get(0).getItem().getPrice();
        address = order.getAddress();
        orderPrice = order.getTotalPrice();
        orderCount = order.getOrderItems().get(0).getCount();
    }
}
