package assignment.shop.order.dto.response;

import assignment.shop.order.Address;
import assignment.shop.order.Order;
import assignment.shop.order.OrderItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderHistoryResponse {
    private final Long orderId;

    private final List<OrderSingleHistoryResponse> orderHistories;

    private final Address address;

    public static OrderHistoryResponse from(Order order) {
        List<OrderSingleHistoryResponse> orderHistories = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            orderHistories.add(OrderSingleHistoryResponse.from(orderItem));
        }
        return new OrderHistoryResponse(order.getId(), orderHistories, order.getAddress());
    }
}
