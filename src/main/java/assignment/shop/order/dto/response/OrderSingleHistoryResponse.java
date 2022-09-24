package assignment.shop.order.dto.response;

import assignment.shop.order.OrderItem;
import lombok.Data;

@Data
public class OrderSingleHistoryResponse {
    private final String itemName;

    private final Integer itemPrice;

    private final Integer orderPrice;

    private final Integer orderQuantity;

    public static OrderSingleHistoryResponse from(OrderItem orderItem) {
        return new OrderSingleHistoryResponse(
                orderItem.getItem().getName(),
                orderItem.getItem().getPrice(),
                orderItem.getTotalPrice(),
                orderItem.getItem().getStockQuantity());
    }
}
