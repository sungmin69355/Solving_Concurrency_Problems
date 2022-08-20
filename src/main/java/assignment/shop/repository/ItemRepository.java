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
        if(item.getId() == null){ //처음에는 id가 없다.
            em.persist(item);
        }else{
            em.merge(item); //아이디 값이있으면 업데이트 비슷하게!
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

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
