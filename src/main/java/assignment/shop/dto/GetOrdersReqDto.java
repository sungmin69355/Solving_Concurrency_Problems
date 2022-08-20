package assignment.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetOrdersReqDto {
    private Long memberId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
