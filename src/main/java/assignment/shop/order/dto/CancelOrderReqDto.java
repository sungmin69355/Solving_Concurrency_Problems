package assignment.shop.order.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CancelOrderReqDto {
    @NotNull
    private Long orderId;
    @NotNull
    private int cancelPrice;
}
