package assignment.shop.dto.order;

import assignment.shop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

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
    private int count;
}
