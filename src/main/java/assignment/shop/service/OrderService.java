package assignment.shop.service;

import assignment.shop.domain.Address;
import assignment.shop.domain.Item;
import assignment.shop.domain.Order;
import assignment.shop.domain.OrderItem;
import assignment.shop.repository.ItemRepository;
import assignment.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new IllegalStateException("없는 상품입니다.");
        }
        //Todo:히스토리 관리 필요

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
        //TODO: 히스토리 관리 필요, 주문이 맞는지 확인
        //주문 취소
        order.cancel();
    }
}