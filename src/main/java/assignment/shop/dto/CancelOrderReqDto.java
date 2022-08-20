package assignment.shop.dto;

import lombok.Data;

@Data
public class CancelOrderReqDto {
    private Long orderId;
    private int cancelPrice;
}
