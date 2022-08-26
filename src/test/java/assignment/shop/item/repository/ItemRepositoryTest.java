package assignment.shop.item.repository;

import assignment.shop.domain.Item;
import assignment.shop.repository.ItemRepository;
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

import static org.springframework.test.util.AssertionErrors.assertEquals;


@WebAppConfiguration
@SpringBootTest
@Transactional
public class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;


    @Test
    void 전시기간상품조회시_전시상품이_있는경우() throws Exception{
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime displayDate =  LocalDateTime.parse("2022-08-11T00:00:00", formatter);

        //when
        List<Item> item  = itemRepository.findDisplayDate(displayDate);

        //then
        assertEquals("전시기간에 상품이 있는경우", item.size(), 4);
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
}