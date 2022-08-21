package assignment.shop.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetItemDisplayDateReqDto {
    @JsonProperty("display_date")
    private LocalDateTime displayDate;
}
