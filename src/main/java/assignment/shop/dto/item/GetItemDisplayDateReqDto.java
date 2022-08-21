package assignment.shop.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class GetItemDisplayDateReqDto {
    @NotNull
    @JsonProperty("display_date")
    private LocalDateTime displayDate;
}
