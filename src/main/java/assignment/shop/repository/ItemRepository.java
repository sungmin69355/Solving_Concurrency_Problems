package assignment.shop.repository;

import assignment.shop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){ //새로 생성된 객체
        if(item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findDisplayDate(LocalDateTime displayDate){
        return em.createQuery("select i from Item i " +
                "where i.displayStartDate <= :displayDate and  i.displayEndDate >= :displayDate", Item.class)
                .setParameter("displayDate", displayDate)
                .getResultList();
    }
}
