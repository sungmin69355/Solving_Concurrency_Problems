package assignment.shop.config;

import assignment.shop.domain.Item;
import assignment.shop.domain.ItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class InitItemDB {
    private final InitService initService;

    @PostConstruct // WAS가 실행했을 떄 한번만 실행
    public void init() {
        initService.dbInitItem();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInitItem() {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            Item item1 = createItem("맥북프로", 3400000L, 100, ItemStatus.SALE,
                    1001L, "애플", LocalDateTime.parse("2022-08-10T00:00:00", formatter),LocalDateTime.parse("2025-12-31T23:59:59", formatter));
            em.persist(item1);

            Item item2 = createItem("문화상품권", 10000L, 10, ItemStatus.SALE,
                    1002L, "기프티콘", LocalDateTime.parse("2022-08-10T00:00:00", formatter),LocalDateTime.parse("2025-12-31T23:59:59", formatter));
            em.persist(item2);

            Item item3 = createItem("(아마존)Corsair qoswjstm LPX 테스크탑 메모리32GB(2X16GB) DDR4 3200(PC4-288000) C18 1.35V블랙", 143880L, 50000, ItemStatus.SALE,
                    1003L, "하이닉스", LocalDateTime.parse("2022-08-10T00:00:00", formatter),LocalDateTime.parse("2025-12-31T23:59:59", formatter));
            em.persist(item3);
        }

        private Item createItem(String name, Long price, int stockQuantity, ItemStatus status, Long sellerId, String sellerName, LocalDateTime displayStartDate, LocalDateTime displayEndDate){
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

}
