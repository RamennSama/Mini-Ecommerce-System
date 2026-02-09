package com.ramennsama.springboot.system.mapper;

import com.ramennsama.springboot.system.dto.response.OrderItemResponse;
import com.ramennsama.springboot.system.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductMapper.class}
)
public interface OrderItemMapper {

    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
