package assignment.shop.order.controller;

import assignment.shop.order.Order;
import assignment.shop.order.OrderStatus;
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

    private final OrderService orderService;

    /**
     * 주문요청 API
     * @param createOrderReqDto
     * @return ResultDto
     */
    @PostMapping("/orders")
    public ResultDto createOrder(@RequestHeader(name = "x-user-id") String user,
                                 @Valid @RequestBody CreateOrderReqDto createOrderReqDto) {
        if(!user.equals("greatepeople")){
            return new ResultDto("401", "올바르지 않은 유저입니다.");
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
    public ResultDto cancelOrder(@RequestHeader(name = "x-user-id") String user,
                                 @Valid @RequestBody CancelOrderReqDto cancelOrderReqDto){

        if(!user.equals("greatepeople")) {
            return new ResultDto("401", "올바르지 않은 유저입니다.");
        }

        //TODO : 주문한사람과 취소한사람이 같은지 검증필요
        Order order = orderService.findOne(cancelOrderReqDto.getOrderId());

        if(order == null){
            return new ResultDto("400", "주문내역이 없습니다.");
        } else if(order.getTotalPrice() != cancelOrderReqDto.getCancelPrice()) {
            return new ResultDto("400", "취소금액이 맞지않습니다.");
        } else if(order.getStatus().equals(OrderStatus.CANCEL)){
            return new ResultDto("400", "이미 취소한 주문입니다.");
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
    public ResultDto getOrders(@RequestParam("memeber_id)") Long memberId,
                               @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                               @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    )  {

        //TODO: 주문취소내역도 같이보여줘야하는지
        List<Order> orders = orderService.findUserOrders(memberId, startDate, endDate);
        List<GetOrdersResDto> result = orders.stream()
                .map(o -> new GetOrdersResDto(o))
                .collect(toList());

        return new ResultDto("200", result);
    }

}