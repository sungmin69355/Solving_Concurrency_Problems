package assignment.shop.item.controller;

import assignment.shop.item.Item;
import assignment.shop.item.dto.ItemResponse;
import assignment.shop.item.dto.ResultDto;
import assignment.shop.item.service.ItemService;
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
public class ItemController {

    private final ItemService itemService;

    /**
     * 전시기간 상품 조회 API
     * @param displayDate
     * @return ResultDto
     */
    @GetMapping("/items")
    public ResultDto getItemDisplayDate(@RequestParam("display_date")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime displayDate){
        List<ItemResponse> result = itemService.findDisplayDate(displayDate);
        return new ResultDto("200", result);
    }
}
