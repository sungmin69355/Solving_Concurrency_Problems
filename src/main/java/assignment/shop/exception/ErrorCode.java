package assignment.shop.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {

    NO_SEARCHING_ITEM("I001", "해당 상품을 찾을수없습니다.", 400),
    NOT_FOUND_ORDER("O001","해당 주문을 찾을 수 없습니다.", 400),
    INVALID_INPUT_VALUE("C001", "요청이 올바르지 않습니다", 400);

    private final String code;
    private final String message;
    private final int status;
}
