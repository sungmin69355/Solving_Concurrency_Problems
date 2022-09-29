package assignment.shop.order.controller;

import assignment.shop.exception.ErrorCode;
import assignment.shop.exception.OrderException;
import assignment.shop.order.Order;
import assignment.shop.order.dto.request.CancelOrderReqDto;
import assignment.shop.order.dto.request.CreateOrderRequest;
import assignment.shop.order.dto.response.OrderHistoryResponse;
import assignment.shop.order.dto.response.OrderResponse;
import assignment.shop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final String USER_ID_HEADER = "x-user-id";

    private final OrderService orderService;

    /**
     * 주문요청 API
     * @param createOrderRequest
     * @return ResultDto
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader(USER_ID_HEADER) String memberId,
                                                     @Valid @RequestBody CreateOrderRequest createOrderRequest) {
        if(!memberId.equals("greatepeople")){
            throw new OrderException(ErrorCode.INVALID_USER);
        } else {
            //임의의 유저
            createOrderRequest.setMemberId(1L);
        }

        OrderResponse response = orderService.order(createOrderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 주문취소요청 API
     * @param cancelOrderReqDto
     * @return ResultDto
     */
    @PostMapping("/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@RequestHeader(USER_ID_HEADER) String memberId,
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

        OrderResponse orderResponse = orderService.cancelOrder(cancelOrderReqDto.getOrderId());

        return ResponseEntity.ok(orderResponse);
    }

    /**
     * 유저 주문내역 조회[페이징] API
     * @param memberId
     * @param startDate
     * @param endDate
     * @param pageable
     * @return
     */
    @GetMapping
    public Page<OrderHistoryResponse> getOrderHistory(@RequestHeader(USER_ID_HEADER) Long memberId,
                                                      @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                      @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                      @PageableDefault(size = 5) Pageable pageable) {
        if (startDate.isAfter(endDate)) {
            throw new OrderException(ErrorCode.INVALID_INPUT_VALUE);
        }
        return orderService.getOrderHistory(memberId, startDate, endDate, pageable);
    }

}
