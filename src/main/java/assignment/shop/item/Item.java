package assignment.shop.item;

import assignment.shop.common.commonEntity;
import assignment.shop.exception.ApiException;
import assignment.shop.exception.ErrorCode;
import assignment.shop.exception.ItemException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Audited
public class Item extends commonEntity {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Version
    private int version;

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
            throw new ItemException(ErrorCode.MANY_QUANTITY_ITEM_SELECT);
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
            throw new ItemException(ErrorCode.NOT_ENOUGH_INPUT_PRICE);
        }
        if((price * OrderCount) != orderPrice ){
            throw new ItemException(ErrorCode.CHECK_THE_ORDER_QUANTITY_PRICE);
        }
    }

}
