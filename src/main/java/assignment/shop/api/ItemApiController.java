package assignment.shop.api;

import assignment.shop.domain.Item;
import assignment.shop.dto.item.GetItemDisplayDateReqDto;
import assignment.shop.dto.item.ItemDto;
import assignment.shop.dto.common.ResultDto;
import assignment.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    /**
     * 전시기간 상품 조회 API
     * @param getItemDisplayDateReqDto
     * @return ResultDto
     */
    @GetMapping("/items")
    public ResultDto getItemDisplayDate(@Valid @RequestBody
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) GetItemDisplayDateReqDto getItemDisplayDateReqDto){
        List<Item> items = itemService.findDisplayDate(getItemDisplayDateReqDto.getDisplayDate());
        List<ItemDto> result = items.stream()
                .map(i -> new ItemDto(i))
                .collect(toList());
        return new ResultDto("200", result);
    }
}
