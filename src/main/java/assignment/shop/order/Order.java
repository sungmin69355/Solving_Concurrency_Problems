package assignment.shop.order;

import assignment.shop.common.commonEntity;
import assignment.shop.exception.ErrorCode;
import assignment.shop.exception.OrderException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Audited
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends commonEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private Long memberId;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    @Audited
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    //==연관관계 메서드==//
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Long memberId, Address address, OrderItem... orderItems){
        Order order = new Order();
        order.setMemberId(memberId);
        order.setAddress(address);
        for(OrderItem orderItem: orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel(){
        checkStatus();

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: orderItems){
            orderItem.cencel();
        }
    }

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    private void checkStatus() {
        if (status == OrderStatus.CANCEL) {
            throw new OrderException(ErrorCode.NOT_AVAILABLE_CANCEL);
        }
    }


}
