package assignment.shop.service;

import assignment.shop.domain.Item;
import assignment.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> findDisplayDate(LocalDateTime displayDate){
        return itemRepository.findDisplayDate(displayDate);
    }

}
