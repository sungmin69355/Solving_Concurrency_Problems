package assignment.shop.order.repository;

import assignment.shop.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o from Order o " +
            " join fetch o.orderItems oi " +
            " join fetch oi.item i" +
            " where o.orderDate >= :startDate and o.orderDate <= :endDate and o.memberId  = :memberId")
    List<Order> findUserOrders(LocalDateTime startDate, LocalDateTime endDate, Long memberId);

}
