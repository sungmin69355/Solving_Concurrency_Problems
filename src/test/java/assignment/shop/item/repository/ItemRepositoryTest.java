package assignment.shop.item.repository;

import assignment.shop.item.Item;
import assignment.shop.item.ItemStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @BeforeEach()
    public void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        Item insertItem = createItem("맥북프로", 3400000, 100, ItemStatus.SALE,
                99999L, "애플", LocalDateTime.parse("2022-08-10T00:00:00", formatter),LocalDateTime.parse("2025-12-31T23:59:59", formatter));

        itemRepository.save(insertItem);

    }

    @Test
    void 전시기간상품조회시_전시상품이_있는경우() throws Exception{
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime displayDate =  LocalDateTime.parse("2022-08-15T00:00:00", formatter);

        //when
        List<Item> item  = itemRepository.findDisplayDate(displayDate);

        //then
        assertEquals("전시기간에 상품이 있는경우", item.size(), 1);
    }

    @Test
    void 전시기간상품조회시_전시상품이_없는경우() throws Exception{
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime displayDate =  LocalDateTime.parse("2022-08-09T00:00:00", formatter);

        //when
        List<Item> item  = itemRepository.findDisplayDate(displayDate);

        //then
        assertEquals("전시기간에 상품이 없는경우",item.size(), 0);
    }

    private Item createItem(String name, int price, int stockQuantity, ItemStatus status, Long sellerId, String sellerName, LocalDateTime displayStartDate, LocalDateTime displayEndDate){
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        item.setStatus(status);
        item.setSellerId(sellerId);
        item.setSellerName(sellerName);
        item.setDisplayStartDate(displayStartDate);
        item.setDisplayEndDate(displayEndDate);
        return item;
    }
}