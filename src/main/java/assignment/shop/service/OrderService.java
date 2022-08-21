package assignment.shop.service;

import assignment.shop.domain.*;
import assignment.shop.dto.common.ResultDto;
import assignment.shop.exception.ApiException;
import assignment.shop.repository.ItemRepository;
import assignment.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     *  상품 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int orderPrice , Address address, int count){
        //상품 조회
        Item item = itemRepository.findOne(itemId);

        if(item == null){
            throw new ApiException(HttpStatus.ACCEPTED, "202", "없는 상품입니다.");
        }

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderPrice, count);

        //주문 생성
        Order order = Order.createOrder(memberId, address, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 조회
        Order order = orderRepository.findOne(orderId);
        if(order.getStatus() == OrderStatus.CANCEL){
            throw new ApiException(HttpStatus.BAD_REQUEST, "400", "이미 취소한 주문입니다.");
        }
        //주문 취소
        order.cancel();
    }

    /**
     * 주문내역 기간으로조회
     */
    public List<Order> findUserOrders(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findUserOrders(memberId,startDate,endDate);
        return orders;
    }

    /**
     * 주문내역 조회
     */
    public Order findOne(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        return order;
    }
}
