package assignment.shop.dto;

import assignment.shop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrderReqDto {

    private Long memberId;
    private Long itemId;
    private int orderPrice;
    private Address address;
    private int count;
}
