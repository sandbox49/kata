package com.kata.estore.integration;

import com.kata.estore.domain.model.CustomerType;
import com.kata.estore.domain.model.ItemType;
import com.kata.estore.infra.rest.dto.CartPricingRequest;
import com.kata.estore.infra.rest.dto.CustomerDto;
import com.kata.estore.infra.rest.dto.ItemRequestDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartRequestIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testValidCartWithMoralCustomer() {
        CustomerDto customerDto = new CustomerDto(
                UUID.randomUUID(),
                CustomerType.MORAL,
                "Valid Raison",
                "123456789",
                BigDecimal.valueOf(1000),
                "FR12345678901",
                null,
                null
        );

        CartPricingRequest request = new CartPricingRequest(
                customerDto,
                Map.of(
                        ItemType.LAPTOP, new ItemRequestDto("Vallée", ItemType.LAPTOP, 2),
                        ItemType.HIGH_END_PHONE, new ItemRequestDto("BlingBling", ItemType.HIGH_END_PHONE, 1)
                )
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/cart")
                .then()
                .statusCode(200)
                .body(equalTo("3150"));
    }

    @Test
    void testValidCartWithPhysicalCustomer() {
        CustomerDto customerDto = new CustomerDto(
                UUID.randomUUID(),
                CustomerType.PHYSICAL,
                null,
                null,
                null,
                null,
                "Jean",
                "Bon"
        );

        CartPricingRequest request = new CartPricingRequest(
                customerDto,
                Map.of(
                        ItemType.LAPTOP, new ItemRequestDto("Laptop Model", ItemType.LAPTOP, 1)
                )
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/cart")
                .then()
                .statusCode(200)
                .body(equalTo("1200"));
    }

    @Test
    void testValidCartWithMoralCustomerAndHighRevenue() {
        CustomerDto customerDto = new CustomerDto(
                UUID.randomUUID(),
                CustomerType.MORAL,
                "Big Corp",
                "123456789",
                BigDecimal.valueOf(1_500_000),
                "FR12345678901",
                null,
                null
        );

        CartPricingRequest request = new CartPricingRequest(
                customerDto,
                Map.of(
                        ItemType.HIGH_END_PHONE, new ItemRequestDto("Phone Model", ItemType.HIGH_END_PHONE, 3)
                )
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/cart")
                .then()
                .statusCode(200)
                .body(equalTo("3000"));
    }

    @Test
    void testInvalidCartWithMoralCustomer() {
        CustomerDto customerDto = new CustomerDto(
                UUID.randomUUID(),
                CustomerType.MORAL,
                null,
                null,
                null,
                null,
                "",
                ""
        );

        CartPricingRequest request = new CartPricingRequest(
                customerDto,
                Map.of(
                        ItemType.LAPTOP, new ItemRequestDto(null, ItemType.LAPTOP, -1)
                )
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/cart")
                .then()
                .statusCode(400);
    }

    @Test
    void testInvalidCartWithPhysicalCustomer() {
        CustomerDto customerDto = new CustomerDto(
                UUID.randomUUID(),
                CustomerType.PHYSICAL,
                null,
                null,
                null,
                null,
                "",
                ""
        );

        CartPricingRequest request = new CartPricingRequest(
                customerDto,
                Map.of(
                        ItemType.LAPTOP, new ItemRequestDto(null, ItemType.LAPTOP, -1)
                )
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/cart")
                .then()
                .statusCode(400);
    }
}