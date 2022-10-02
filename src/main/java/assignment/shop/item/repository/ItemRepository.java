package assignment.shop.item.repository;

import assignment.shop.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Item i where i.id = :id")
    Item findOne(Long id);

    @Query(value = "select i from Item i where  i.displayStartDate <= :displayDate and i.displayEndDate >= :displayDate")
    List<Item> findDisplayDate(LocalDateTime displayDate);
}
