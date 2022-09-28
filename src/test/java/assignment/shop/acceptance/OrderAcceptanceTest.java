package assignment.shop.acceptance;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderAcceptanceTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void 주문_내역을_조회시_기간으로_조건에_맞는_주문이_있으면_주문_페이징_목록이_반환() {
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("x-user-id", "1")
        .when()
                .get("/api/orders/history?start_date=2022-06-20T00:00&end_date=2023-08-21T00:00")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("first", equalTo(true))
                .body("size", equalTo(5));
    }

    @Test
    public void 주문_내역을_조회시_기간으로_조건에_맞는_주문이_없으면_빈_주문_페이징_목록이_반환() {
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("x-user-id", "0")
        .when()
                .get("/api/orders/history?start_date=2022-06-20T00:00&end_date=-08-21T00:00")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("first", equalTo(true))
                .body("last", equalTo(true))
                .body("size", equalTo(5));
    }
}
