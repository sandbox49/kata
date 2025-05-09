package com.kata.estore.infra.rest.mapper;

import com.kata.estore.domain.model.Item;
import com.kata.estore.infra.rest.dto.ItemRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item toEntity(ItemRequestDto itemRequestDto);


}
