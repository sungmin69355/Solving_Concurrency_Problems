# 11st_assignment

1.개발환경
-------------
* Java11, Spring boot 2.73, JPA, H2, Gradle, Juint5

2.ERD
-------------
1. 상품 테이블

```
item (
        item_id bigint not null,
        display_start_date timestamp,
        display_end_date timestamp,
        name varchar(255),
        price integer not null,
        seller_id bigint,
        seller_name varchar(255),
        status varchar(255),
        stock_quantity integer not null,
        primary key (item_id)
    )
```

|컬럼명| 타입           | 컬럼설명                        |
|------|--------------|-----------------------------|
|item_id| bigint | 상품의 ID를 저장합니다(PRIMARY KEY) |
|display_start_date| timestamp | 상품전시시작일을 저장합니다.      |
|display_end_date| timestamp | 상품전시종료일을 저장합니다.        |
|name| varchar(255) | 상품명을 저장합니다. |
|price| integer | 상품가격을 저장합니다.|
|seller_id| bigint | 셀러의 ID 저장합니다.        |
|seller_name| varchar(255) | 셀러의 이름을 저장합니다.     |
|status| varchar(255) | 상품의 상태를 저장합니다.        |
|stock_quantity| integer | 상품의 수량을 저장합니다.        |

2. 주문 테이블

```
orders (
        order_id bigint not null,
        city varchar(255),
        street varchar(255),
        zipcode varchar(255),
        member_id bigint,
        order_date timestamp,
        status varchar(255),
        primary key (order_id)
    )
```

|컬럼명| 타입           | 컬럼설명                        |
|------|--------------|-----------------------------|
|order_id| bigint | 주문 ID를 저장합니다(PRIMARY KEY) |
|city| varchar(255) | 배송지의 시 정보를 저장합니다.      |
|street| varchar(255) | 배송지의 도로명주소 정보를 저장합니다.     |
|zipcode| varchar(255) | 배송지의 우편주소 정보를 저장합니다.   |
|member_id| bigint | 주문자의 ID를 저장합니다.|
|order_date| timestamp | 주문시간을 저장합니다.      |

...작성 중

