package assignment.shop.order.controller;

import assignment.shop.exception.ErrorCode;
import assignment.shop.exception.OrderException;
import assignment.shop.order.Order;
import assignment.shop.order.dto.CancelOrderReqDto;
import assignment.shop.order.dto.CreateOrderReqDto;
import assignment.shop.order.dto.GetOrdersResDto;
import assignment.shop.order.dto.ResultDto;
import assignment.shop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private static final String USER_ID_HEADER = "x-user-id";

    private final OrderService orderService;

    /**
     * 주문요청 API
     * @param createOrderReqDto
     * @return ResultDto
     */
    @PostMapping("/orders")
    public ResultDto createOrder(@RequestHeader(USER_ID_HEADER) String memberId,
                                 @Valid @RequestBody CreateOrderReqDto createOrderReqDto) {
        if(!memberId.equals("greatepeople")){
            throw new OrderException(ErrorCode.INVALID_USER);
        } else {
            //임의의 유저
            createOrderReqDto.setMemberId(1L);
        }

        Long orderId = orderService.order(createOrderReqDto.getMemberId(), createOrderReqDto.getItemId(),
                createOrderReqDto.getOrderPrice(), createOrderReqDto.getAddress(), createOrderReqDto.getCount());

        return new ResultDto("200", orderId);
    }

    /**
     * 주문취소요청 API
     * @param cancelOrderReqDto
     * @return ResultDto
     */
    @PostMapping("/orders/cancel")
    public ResultDto cancelOrder(@RequestHeader(USER_ID_HEADER) String memberId,
                                 @Valid @RequestBody CancelOrderReqDto cancelOrderReqDto){

        if(!memberId.equals("greatepeople")) {
            throw new OrderException(ErrorCode.INVALID_USER);
        }

        //TODO : 주문한사람과 취소한사람이 같은지 검증필요
        Order order = orderService.findOne(cancelOrderReqDto.getOrderId());

        if(order == null){
            throw new OrderException(ErrorCode.NOT_FOUND_ORDER);
        } else if(order.getTotalPrice() != cancelOrderReqDto.getCancelPrice()) {
            throw new OrderException(ErrorCode.CHECK_THE_ORDER_QUANTITY_PRICE);
        }

        orderService.cancelOrder(cancelOrderReqDto.getOrderId());

        return new ResultDto("200", "주문취소가 완료되었습니다.");
    }

    /**
     * 주문 내역 조회 API
     * @param
     * @return
     */
    @GetMapping("/orders")
    public ResultDto getOrders(@RequestHeader(USER_ID_HEADER) Long memberId,
                               @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                               @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    )  {

        //TODO: 주문취소내역도 같이보여줘야하는
        List<Order> orders = orderService.findUserOrders(memberId, startDate, endDate);
        List<GetOrdersResDto> result = orders.stream()
                .map(o -> new GetOrdersResDto(o))
                .collect(toList());

        return new ResultDto("200", result);
    }

}
