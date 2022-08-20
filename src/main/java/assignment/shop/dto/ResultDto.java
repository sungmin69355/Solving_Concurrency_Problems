package assignment.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultDto<T> {
    private String status;
    private String message;
    private T data;

    public ResultDto(String status, List<ItemDto> data) {
        this.status =status;
        this.data = (T) data;
    }

    public ResultDto(String status, Long data) {
        this.status =status;
        this.data = (T) data;
    }

    public ResultDto(String status, String message) {
        this.status =status;
        this.message = message;
    }
}
