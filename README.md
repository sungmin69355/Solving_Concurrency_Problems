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

3. 주문 상품 테이블

```
order_item (
        order_item_id bigint not null,
        count integer not null,
        order_price integer not null,
        item_id bigint,
        oder_id bigint,
        primary key (order_item_id)
    )
```

|컬럼명| 타입           | 컬럼설명                          |
|------|--------------|-------------------------------|
|order_item_id| bigint | 주문 상품의 ID를 저장합니다(PRIMARY KEY) |
|count| integer | 주문상품의 수량을 저장합니다.              |
|order_price| integer | 주문상품의 가격을 저장합니다.              |
|item_id| bigint | 상품 ID를 저장합니다.                 |
|oder_id| bigint | 주문 ID를 저장합니다.                 |

4. 주문히스토리 테이블

```
orders_aud (
        order_id bigint not null,
        rev integer not null,
        revtype tinyint,
        city varchar(255),
        street varchar(255),
        zipcode varchar(255),
        member_id bigint,
        order_date timestamp,
        status varchar(255),
        primary key (order_id, rev)
    )
```

|컬럼명| 타입           | 컬럼설명                        |
|------|--------------|-----------------------------|
|order_id| bigint | 주문 ID를 저장합니다.(PRIMARY KEY)  |
|rev | integer | 이력번호를 저장합니다.(PRIMARY KEY)   |
|revtype | tinyint | 추가, 수정, 삭제의 대한 type을 저장합니다. |
|city| varchar(255) | 배송지의 시 정보를 저장합니다.           |
|street| varchar(255) | 배송지의 도로명주소 정보를 저장합니다.       |
|zipcode| varchar(255) | 배송지의 우편주소 정보를 저장합니다.        |
|member_id| bigint | 주문자의 ID를 저장합니다.             |
|order_date| timestamp | 주문시간을 저장합니다.                |

5. 주문상품히스토리 테이블

```
order_item_aud (
        order_item_id bigint not null,
        rev integer not null,
        revtype tinyint,
        count integer,
        order_price integer,
        item_id bigint,
        oder_id bigint,
        primary key (order_item_id, rev)
    )
```

| 컬럼명           | 타입           | 컬럼설명                          |
|---------------|--------------|-------------------------------|
| order_item_id | bigint | 주문 상품의 ID를 저장합니다(PRIMARY KEY) |
|rev | integer | 이력번호를 저장합니다.(PRIMARY KEY)                |
|revtype | tinyint | 추가, 수정, 삭제의 대한 type을 저장합니다. |
| count         | integer | 주문상품의 수량을 저장합니다.              |
| order_price   | integer | 주문상품의 가격을 저장합니다.              |
| item_id       | bigint | 상품 ID를 저장합니다.                 |
| oder_id       | bigint | 주문 ID를 저장합니다.                 |

6. 상품히스토리 테이블

```
item_aud (
        item_id bigint not null,
        rev integer not null,
        revtype tinyint,
        display_end_date timestamp,
        display_start_date timestamp,
        name varchar(255),
        price integer,
        seller_id bigint,
        seller_name varchar(255),
        status varchar(255),
        stock_quantity integer,
        primary key (item_id, rev)
    )
```

|컬럼명| 타입           | 컬럼설명                        |
|------|--------------|-----------------------------|
|item_id| bigint | 상품의 ID를 저장합니다(PRIMARY KEY) |
|rev | integer | 이력번호를 저장합니다.(PRIMARY KEY)                |
|revtype | tinyint | 추가, 수정, 삭제의 대한 type을 저장합니다. |
|display_start_date| timestamp | 상품전시시작일을 저장합니다.      |
|display_end_date| timestamp | 상품전시종료일을 저장합니다.        |
|name| varchar(255) | 상품명을 저장합니다. |
|price| integer | 상품가격을 저장합니다.|
|seller_id| bigint | 셀러의 ID 저장합니다.        |
|seller_name| varchar(255) | 셀러의 이름을 저장합니다.     |
|status| varchar(255) | 상품의 상태를 저장합니다.        |
|stock_quantity| integer | 상품의 수량을 저장합니다.        |

3.API Spec Document
-------------
### Request
* 전시상품조회 `GET /item?display_date=2022-08-10T00:00:00`
#### INPUT
```
GET {URL}
content-type: application/json
{
}
```
#### OUTPUT
```
{
    "status": "200",
    "message": null,
    "data": [
        {
            "id": 1,
            "name": "맥북프로",
            "price": 3400000,
            "stockQuantity": 100,
            "status": "SALE",
            "sellerId": 1001,
            "sellerName": "애플",
            "displayStartDate": "2022-08-10T00:00:00",
            "displayEndDate": "2025-12-31T23:59:59"
        },
        {
            "id": 2,
            "name": "문화상품권",
            "price": 10000,
            "stockQuantity": 10,
            "status": "SALE",
            "sellerId": 1002,
            "sellerName": "기프티콘",
            "displayStartDate": "2022-08-10T00:00:00",
            "displayEndDate": "2025-12-31T23:59:59"
        },
        {
            "id": 3,
            "name": "(아마존)Corsair qoswjstm LPX 테스크탑 메모리32GB(2X16GB) DDR4 3200(PC4-288000) C18 1.35V블랙",
            "price": 143880,
            "stockQuantity": 50000,
            "status": "SALE",
            "sellerId": 1003,
            "sellerName": "하이닉스",
            "displayStartDate": "2022-08-10T00:00:00",
            "displayEndDate": "2025-12-31T23:59:59"
        }
    ]
}
```
### Request
* 주문요청 `POST /orders`
#### INPUT
```
POST {URL}
-H x-user-id : greatepeople
content-type: application/json
{
    "itemId" : "1",
    "orderPrice" : 3400000,
    "address" : { "city" : "서울시",
                "street" : "아리수로",
                "zipcode" : "205610"
    },
    "count" : 1
}
```
#### OUTPUT
```
{
    "status": "200",
    "message": null,
    "data": 5
}
```
### Request
* 주문취소 `POST /orders/cancel`
#### INPUT
```
POST {URL}
-H x-user-id : greatepeople
content-type: application/json
{
    "orderId" : 5,
    "cancelPrice" : 3400000
}
```
#### OUTPUT
```
{
    "status": "200",
    "message": "주문취소가 완료되었습니다.",
    "data": null
}
```
### Request
* 주문내역조회 `POST /orders`
#### INPUT
```
POST {URL}
-H x-user-id : greatepeople
content-type: application/json
{
    "memberId" : "1",
    "startDate" : "2022-08-09T00:00:00",
    "endDate" : "2022-08-30T00:00:00"
}
```
#### OUTPUT
```
{
    "status": "200",
    "message": null,
    "data": [
        {
            "orderId": 5,
            "itemName": "맥북프로",
            "itemPrice": 3400000,
            "address": {
                "city": "서울시",
                "street": "아리수로",
                "zipcode": "205610"
            },
            "orderPrice": 3400000,
            "orderCount": 1
        }
    ]
}
```