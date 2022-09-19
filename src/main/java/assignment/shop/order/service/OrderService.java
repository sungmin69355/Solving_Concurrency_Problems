package assignment.shop.order.service;

import assignment.shop.exception.ErrorCode;
import assignment.shop.exception.NoSuchEntityException;
import assignment.shop.exception.OrderException;
import assignment.shop.item.Item;
import assignment.shop.order.Address;
import assignment.shop.order.Order;
import assignment.shop.order.OrderItem;
import assignment.shop.item.repository.ItemRepository;
import assignment.shop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
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
            throw new OrderException(ErrorCode.NO_SEARCHING_ITEM);
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
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchEntityException(ErrorCode.NOT_FOUND_ORDER));
        //주문 취소
        order.cancel();
    }

    /**
     * 주문내역 기간으로조회
     */
    public List<Order> findUserOrders(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findUserOrders(startDate,endDate, memberId);
        return orders;
    }

    /**
     * 주문내역 조회
     */
    public Order findOne(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchEntityException(ErrorCode.NOT_FOUND_ORDER));
        return order;
    }
}
