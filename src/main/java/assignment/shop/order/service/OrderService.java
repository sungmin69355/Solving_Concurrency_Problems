package assignment.shop.order.service;

import assignment.shop.exception.ErrorCode;
import assignment.shop.exception.NoSuchEntityException;
import assignment.shop.exception.OrderException;
import assignment.shop.item.Item;
import assignment.shop.order.Address;
import assignment.shop.order.Order;
import assignment.shop.order.OrderItem;
import assignment.shop.item.repository.ItemRepository;
import assignment.shop.order.dto.request.CreateOrderRequest;
import assignment.shop.order.dto.response.GetOrdersResponse;
import assignment.shop.order.dto.response.OrderHistoryResponse;
import assignment.shop.order.dto.response.OrderResponse;
import assignment.shop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     *  상품 주문
     * @return
     */
    @Transactional
    public OrderResponse order(CreateOrderRequest createOrderRequest){
        //상품 조회
        Item item = itemRepository.findOne(createOrderRequest.getItemId());

        if(item == null){
            throw new OrderException(ErrorCode.NO_SEARCHING_ITEM);
        }

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, createOrderRequest.getOrderPrice(), createOrderRequest.getCount());

        //주문 생성
        Order order = Order.createOrder(createOrderRequest.getMemberId(), createOrderRequest.getAddress(), orderItem);

        orderRepository.save(order);
        return OrderResponse.from(order.getId());
    }

    /**
     * 주문 취소
     */
    @Transactional
    public OrderResponse cancelOrder(Long orderId){
        //주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchEntityException(ErrorCode.NOT_FOUND_ORDER));
        //주문 취소
        order.cancel();
        return OrderResponse.from(order.getId());
    }

    /**
     * 주문내역 기간으로조회
     */
    public List<GetOrdersResponse> findUserOrders(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findUserOrders(startDate,endDate, memberId);
        List<GetOrdersResponse> result = orders.stream()
                .map(o -> new GetOrdersResponse(o))
                .collect(toList());
        return result;
    }

    /**
     * 주문내역 조회
     */
    public Order findOne(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchEntityException(ErrorCode.NOT_FOUND_ORDER));
        return order;
    }

    /**
     *  유저의 주문내역 조회 페이징 처리
     */
    public Page<OrderHistoryResponse> getOrderHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserIdAndDateCondition(memberId, startDate, endDate, pageable);
        return orders.map(OrderHistoryResponse::from);
    }
}
