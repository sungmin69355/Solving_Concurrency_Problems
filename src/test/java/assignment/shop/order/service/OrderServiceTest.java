package assignment.shop.order.service;

import assignment.shop.domain.Address;
import assignment.shop.domain.Item;
import assignment.shop.domain.Order;
import assignment.shop.domain.OrderStatus;
import assignment.shop.exception.ApiException;
import assignment.shop.repository.ItemRepository;
import assignment.shop.repository.OrderRepository;
import assignment.shop.service.OrderService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;

@WebAppConfiguration
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품주문() throws Exception {

        //given
        Long memberId = 100L;
        Long itemId = 1L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 3400000;
        int count = 1;

        //when
        Long orderId = orderService.order(memberId, itemId, orderPrice, address, count);

        //then
        Order order = orderRepository.findOne(orderId);

        Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, order.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 * 수량이다.", orderPrice, order.getTotalPrice());
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 1L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 3400000;
        int count = 110;

        //then
        assertThrows(ApiException.class, ()->{
            orderService.order(memberId, itemId, orderPrice, address, count);//예외가 발생해야 한다.
        });
    }

    @Test
    public void 상품주문_가격부족() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 1L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 34000;
        int count = 100;

        //then
        assertThrows(ApiException.class, ()->{
            orderService.order(memberId, itemId, orderPrice, address, count);//예외가 발생해야 한다.
        });
    }

    @Test
    public void 없는상품으로_주문시_실패() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 500000L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 34000;
        int count = 100;

        //then
        assertThrows(ApiException.class, ()->{
            orderService.order(memberId, itemId, orderPrice, address, count);//예외가 발생해야 한다.
        });
    }

    @Test
    public void 판매중지인_상품주문시_실패() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 4L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 34000;
        int count = 100;

        //then
        assertThrows(ApiException.class, ()->{
            orderService.order(memberId, itemId, orderPrice, address, count);//예외가 발생해야 한다.
        });
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 1L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 3400000;
        int count = 1;

        //when
        Long orderId = orderService.order(memberId, itemId, orderPrice, address, count);
        orderService.cancelOrder(orderId);
        Order order = orderRepository.findOne(orderId);
        Item item = itemRepository.findOne(1L);


        //then
        Assert.assertEquals("주문 취소시 상태는 CANCEL 이다. ", OrderStatus.CANCEL, order.getStatus());
        Assert.assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 100, item.getStockQuantity());
    }

    @Test
    public void 주문취소는_한번만보장() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 1L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 3400000;
        int count = 1;

        //when
        Long orderId = orderService.order(memberId, itemId, orderPrice, address, count);
        orderService.cancelOrder(orderId);

        //then
        assertThrows(ApiException.class, ()->{
            orderService.cancelOrder(orderId);
        });
    }


    @Test
    public void 주문내역조회() throws Exception {
        //given
        Long memberId = 100L;
        Long itemId = 1L;
        Address address = new Address("서울시" ,"1번가", "205-106");
        int orderPrice = 3400000;
        int count = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDate =  LocalDateTime.parse("2010-08-15T00:00:00", formatter);
        LocalDateTime endDate =  LocalDateTime.parse("2050-08-15T00:00:00", formatter);

        //when
        Long orderId = orderService.order(memberId, itemId, orderPrice, address, count);
        List<Order> orders =  orderService.findUserOrders(memberId, startDate, endDate);

        //then
        Assert.assertEquals("주문내역을 조회할 수 있다.", orders.size(), 1);
    }

}
