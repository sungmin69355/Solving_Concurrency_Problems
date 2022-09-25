package assignment.shop.order.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderResponse {
    private final Long orderId;

    public static OrderResponse from(Long orderId) {
        return new OrderResponse(orderId);
    }
}

