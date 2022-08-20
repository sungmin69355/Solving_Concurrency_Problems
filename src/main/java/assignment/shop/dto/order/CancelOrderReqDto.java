package assignment.shop.dto.order;

import lombok.Data;

@Data
public class CancelOrderReqDto {
    private Long orderId;
    private int cancelPrice;
}
