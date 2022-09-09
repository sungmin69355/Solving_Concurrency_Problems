package assignment.shop.order.repository;

import assignment.shop.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findUserOrders(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createQuery("select o from Order o " +
                        " join fetch o.orderItems oi " +
                        " join fetch oi.item i" +
                        " where o.orderDate >= :startDate and o.orderDate <= :endDate and o.memberId  = :memberId ", Order.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("memberId", memberId)
                .getResultList();
    }

}
