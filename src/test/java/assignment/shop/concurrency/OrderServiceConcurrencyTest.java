package assignment.shop.concurrency;


import assignment.shop.order.Address;
import assignment.shop.order.Order;
import assignment.shop.item.repository.ItemRepository;
import assignment.shop.order.repository.OrderRepository;
import assignment.shop.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@WebAppConfiguration
@SpringBootTest
@Transactional
public class OrderServiceConcurrencyTest {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 재고가_100개_있는상품을_동시에_10개씩_80번이상주문하면_10개의주문만_성공되어야한다() throws Exception {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDate =  LocalDateTime.parse("1999-08-15T00:00:00", formatter);
        LocalDateTime endDate =  LocalDateTime.parse("9999-12-31T00:00:00", formatter);
        Long memberId = 100L;
        Long itemId = 1L;
        Address address = new Address("서울시" ,"100길", "205-106");
        int orderPrice = 34000000;
        int count = 10;

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32); //고정된 쓰레드 풀 생성
        CountDownLatch countDownLatch = new CountDownLatch(threadCount); //어떤 쓰레드가 다른 쓰레드에서 작업이 완료될 때 까지 기다릴 수 있도록 해주는 클래스

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(()->{
                try {
                    orderService.order(memberId, itemId, orderPrice, address, count);
                } finally {
                    countDownLatch.countDown(); //Latch가 1개씩 감소된다.
                }
            });
        }
        countDownLatch.await(); //Latch가 0이될때까지 기다린다.
        //when
        List<Order> orders = orderRepository.findUserOrders(100L, startDate, endDate);

        //then
        assertEquals("주문이 10개만 성립되어야한다.",orders.size(), 10);


    }


}
