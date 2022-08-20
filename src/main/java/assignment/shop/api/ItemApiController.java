package assignment.shop.api;

import assignment.shop.domain.Item;
import assignment.shop.dto.ItemDto;
import assignment.shop.dto.ResultDto;
import assignment.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    /**
     * 전시기간 상품 조회 API
     * @param displayDate
     * @return ResultDto
     */
    @GetMapping("/item")
    public ResultDto getItemDisplayDate(@RequestParam("display_date")
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime displayDate){
        List<Item> items = itemService.findDisplayDate(displayDate);
        List<ItemDto> result = items.stream()
                .map(i -> new ItemDto(i))
                .collect(toList());
        return new ResultDto("200", result);
    }
}
