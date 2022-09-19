package assignment.shop.order;

import assignment.shop.exception.ErrorCode;
import assignment.shop.exception.OrderException;
import assignment.shop.item.Item;
import assignment.shop.item.ItemStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name= "order_item_id")
    private Long id;

    @JsonIgnore
    @Audited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "oder_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //== 셍성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        if(item.getStatus() == ItemStatus.SOLDOUT){
            throw new OrderException(ErrorCode.SUSPENDED_PRODUCT);
        }
        item.TotalPriceValidation(count, orderPrice); //가격검증
        item.removeStock(count); //아이템의 재고를 줄여준다.
        return orderItem;
    }

    //==비지니스 로직==//
    public void cencel() {
        //재고 수량을 원복
        getItem().addStock(count);
    }

    /**
     *  주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
