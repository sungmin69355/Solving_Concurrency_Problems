package assignment.shop.order.service;

import assignment.shop.exception.ItemException;
import assignment.shop.exception.NoSuchEntityException;
import assignment.shop.exception.OrderException;
import assignment.shop.order.Address;
import assignment.shop.item.Item;
import assignment.shop.order.Order;
import assignment.shop.order.OrderStatus;
import assignment.shop.item.repository.ItemRepository;
import assignment.shop.order.dto.request.CreateOrderRequest;
import assignment.shop.order.dto.response.OrderResponse;
import assignment.shop.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@WebAppConfiguration
@SpringBootTest
@Transactional(readOnly = true)
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    @Transactional
    public void 상품주문() throws Exception {

        //given
        Long memberId = 100L;
        Long itemId = 2L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 10000;
        int count = 1;

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(memberId, itemId, orderPrice, address, count);

        //when
        OrderResponse orders = orderService.order(createOrderRequest);

        //then
        Order order = orderRepository.findById(orders.getOrderId()).get();

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, order.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", orderPrice, order.getTotalPrice());
    }

    @Test
    @Transactional
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 2L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 10000;
        int count = 110;

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(memberId, itemId, orderPrice, address, count);

        //then
        assertThrows(ItemException.class, ()->{
            orderService.order(createOrderRequest);//예외가 발생해야 한다.
        });
    }

    @Test
    @Transactional
    public void 상품주문_가격부족() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 2L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 34000;
        int count = 100;

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(memberId, itemId, orderPrice, address, count);


        //then
        assertThrows(ItemException.class, ()->{
            orderService.order(createOrderRequest);//예외가 발생해야 한다.
        });
    }

    @Test
    @Transactional
    public void 없는상품으로_주문시_실패() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 500000L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 34000;
        int count = 100;

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(memberId, itemId, orderPrice, address, count);

        //then
        assertThrows(OrderException.class, ()->{
            orderService.order(createOrderRequest);//예외가 발생해야 한다.
        });
    }

    @Test
    @Transactional
    public void 판매중지인_상품주문시_실패() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 4L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 34000;
        int count = 100;

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(memberId, itemId, orderPrice, address, count);


        //then
        assertThrows(OrderException.class, ()->{
            orderService.order(createOrderRequest);//예외가 발생해야 한다.
        });
    }

    @Test
    @Transactional
    public void 주문취소() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 2L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 10000;
        int count = 1;

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(memberId, itemId, orderPrice, address, count);

        //when
        OrderResponse createOrder = orderService.order(createOrderRequest);
        orderService.cancelOrder(createOrder.getOrderId());
        Order order = orderRepository.findById(createOrder.getOrderId()).get();
        Item item = itemRepository.findOne(2L);


        //then
        assertEquals("주문 취소시 상태는 CANCEL 이다. ", OrderStatus.CANCEL, order.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());
    }

    @Test
    @Transactional
    public void 주문취소는_한번만보장() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 2L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 10000;
        int count = 1;

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(memberId, itemId, orderPrice, address, count);

        //when
        OrderResponse order = orderService.order(createOrderRequest);
        orderService.cancelOrder(order.getOrderId());

        //then
        assertThrows(OrderException.class, ()->{
            orderService.cancelOrder(order.getOrderId());
        });
    }

    @Test
    public void 없는_주문번호_요청시_오류메시지() throws Exception {
        //given
        Long orderId = 1000000L;

        //when
        //Order order = orderService.findOne(orderId);

        //then
        assertThrows(NoSuchEntityException.class, ()->{
            orderService.findOne(orderId);
        });
    }

}
