package assignment.shop.dto.item;

import assignment.shop.domain.Item;
import assignment.shop.domain.ItemStatus;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    private Long sellerId;
    private String sellerName;

    private LocalDateTime displayStartDate;
    private LocalDateTime displayEndDate;

    public ItemDto(Item item) {
        id = item.getId();
        name = item.getName();
        price = item.getPrice();
        stockQuantity = item.getStockQuantity();
        status = item.getStatus();
        sellerId = item.getSellerId();
        sellerName = item.getSellerName();
        displayStartDate = item.getDisplayStartDate();
        displayEndDate = item.getDisplayEndDate();
    }
}
