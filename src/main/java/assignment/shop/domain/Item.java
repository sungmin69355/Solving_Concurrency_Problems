package assignment.shop.domain;

import assignment.shop.exception.ApiException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Audited
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
            throw new ApiException(HttpStatus.ACCEPTED, "202", "전시된 상품보다 많은 수량을 선택했습니다.");
        } else if (restStock == 0){
            //판매완료 시 판매상태를 판매중지로 전환
            this.status = ItemStatus.SOLDOUT;
        }
        this.stockQuantity = restStock;
    }

    /**
     * 주문가격 검증
     */
    public void TotalPriceValidation(int OrderCount, int orderPrice) {
        if((price * OrderCount) > orderPrice ){
            throw new ApiException(HttpStatus.ACCEPTED, "202", "가격이 부족합니다.");
        }
        if((price * OrderCount) != orderPrice ){
            throw new ApiException(HttpStatus.ACCEPTED, "202", "수량과 가격을 확인해주세요.");
        }
    }

}
