package assignment.shop.api;

import assignment.shop.dto.CreateOrderReqDto;
import assignment.shop.dto.ResultDto;
import assignment.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    /**
     * 주문요청 API
     * @param createOrderReqDto
     * @return
     */
    @PostMapping("/orders")
    public ResultDto CreateOrder(@RequestHeader(name = "x-user-id") String user,
                                 @RequestBody @Validated CreateOrderReqDto createOrderReqDto) {
        if(!user.equals("greatepeople")){
            return new ResultDto("401", "올바르지 않은 유저입니다.");
        } else {
            createOrderReqDto.setMemberId(1L);
        }

        Long orderId = orderService.order(createOrderReqDto.getMemberId(), createOrderReqDto.getItemId(),
                createOrderReqDto.getOrderPrice(), createOrderReqDto.getAddress(), createOrderReqDto.getCount());

        return new ResultDto("200", orderId);
    }
}
