package assignment.shop.dto.order;

import assignment.shop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class CreateOrderReqDto {

    private Long memberId;
    @NotNull
    private Long itemId;
    @NotNull
    private int orderPrice;
    @NotNull
    private Address address;
    @NotNull
    @Positive
    private int count;
}
