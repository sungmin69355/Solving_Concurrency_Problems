package assignment.shop.item.controller;

import assignment.shop.item.dto.ItemResponse;
import assignment.shop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity getItemDisplayDate(@RequestParam("display_date")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime displayDate){
        List<ItemResponse> result = itemService.findDisplayDate(displayDate);
        return ResponseEntity.ok().body(result);
    }
}
