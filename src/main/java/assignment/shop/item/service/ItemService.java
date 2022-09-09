package assignment.shop.item.service;

import assignment.shop.item.Item;
import assignment.shop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.cache.annotation.Cacheable;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Cacheable(cacheNames = "getItems", key = "#displayDate.toLocalDate().toString()")
    public List<Item> findDisplayDate(LocalDateTime displayDate){
        return itemRepository.findDisplayDate(displayDate);
    }

}
