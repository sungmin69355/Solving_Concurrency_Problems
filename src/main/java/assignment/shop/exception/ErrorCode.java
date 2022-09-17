package assignment.shop.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {

    NO_SEARCHING_ITEM("I001", "해당 상품을 찾을수없습니다.", 400),

    NOT_ENOUGH_QUANTITY("B001", "재고가 부족합니다", 400),
    NOT_ENOUGH_INPUT_PRICE("B002", "입금된 금액이 부족합니다.", 400),
    SUSPENDED_PRODUCT("B003", "판매 중지된 상품입니다", 400),
    CHECK_THE_ORDER_QUANTITY_PRICE("B004", "주문 수량과 가격을 확인해주세요.", 400),
    MANY_QUANTITY_ITEM_SELECT("B004", "전시된 상품보다 많은 수량을 선택했습니다.", 400),

    NOT_FOUND_ORDER("O001","해당 주문을 찾을 수 없습니다.", 400),
    NOT_AVAILABLE_CANCEL("O001", "이미 취소가 완료된 주문입니다.", 400),

    INVALID_INPUT_VALUE("C001", "요청이 올바르지 않습니다", 400);

    private final String code;
    private final String message;
    private final int status;
}
