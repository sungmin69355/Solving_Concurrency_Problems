package assignment.shop.order.dto.response;

import assignment.shop.order.Address;
import assignment.shop.order.Order;
import lombok.Data;

@Data
public class GetOrdersResponse {
    private Long orderId;
    private String itemName;
    private int itemPrice;
    private Address address;
    private int orderPrice;
    private int orderCount;

    public GetOrdersResponse(Order order) {
        orderId = order.getId();
        itemName = order.getOrderItems().get(0).getItem().getName();
        itemPrice = order.getOrderItems().get(0).getItem().getPrice();
        address = order.getAddress();
        orderPrice = order.getTotalPrice();
        orderCount = order.getOrderItems().get(0).getCount();
    }
}
