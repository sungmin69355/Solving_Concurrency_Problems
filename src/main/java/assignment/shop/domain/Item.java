package assignment.shop.domain;

import assignment.shop.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
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

    //==비지니스로직==//
    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("전시된 상품보다 많은 수량을 선택했습니다.");
        }
        this.stockQuantity = restStock;
    }

    /**
     * 주문가격 검증
     */
    public void TotalPriceValidation(int count, int orderPrice) {
        if((price * count) > orderPrice ){
            throw new NotEnoughStockException("가격이 부족합니다.");
        }
    }

}
