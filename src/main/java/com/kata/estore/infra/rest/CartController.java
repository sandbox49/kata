package com.kata.estore.infra.rest;

import com.kata.estore.application.services.CartService;
import com.kata.estore.infra.rest.dto.CartPricingRequest;
import com.kata.estore.infra.rest.mapper.CartMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
@Validated
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @Operation(summary = "Calculate the total price of a cart", description = "Accepts a cart pricing request and returns the total price.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart priced successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "400", description = "Invalid cart request",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal priceCart(@Valid @RequestBody CartPricingRequest request) {
        var cart = cartMapper.toEntity(request);
        return cartService.priceCart(cart);
    }

}
