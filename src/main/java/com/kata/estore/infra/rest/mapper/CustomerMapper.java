package com.kata.estore.infra.rest.mapper;

import com.kata.estore.domain.model.Customer;
import com.kata.estore.domain.model.MoralCustomer;
import com.kata.estore.domain.model.PhysicalCustomer;
import com.kata.estore.infra.rest.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {

    public Customer toEntity(CustomerDto customerDto){
        if (customerDto.customerType() == null) {
            throw new IllegalArgumentException("Customer type cannot be null");
        }

        return switch (customerDto.customerType()) {
            case MORAL -> MoralCustomer.builder()
                    .id(customerDto.customerId())
                    .socialRaison(customerDto.socialRaison())
                    .sirenCode(customerDto.sirenCode())
                    .turnover(customerDto.turnover())
                    .vatNb(customerDto.vatNb())
                    .build();
            case PHYSICAL -> PhysicalCustomer.builder()
                    .id(customerDto.customerId())
                    .firstName(customerDto.firstName())
                    .lastName(customerDto.lastName())
                    .build();
        };
    }

    @Mapping(source = "customerId", target = "id")
    public abstract MoralCustomer toMoralCustomer(CustomerDto customerDto);

    @Mapping(source = "customerId", target = "id")
    public abstract PhysicalCustomer toPhysicalCustomer(CustomerDto customerDto);

}