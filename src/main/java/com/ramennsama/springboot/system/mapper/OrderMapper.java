package com.ramennsama.springboot.system.mapper;

import com.ramennsama.springboot.system.dto.response.OrderResponse;
import com.ramennsama.springboot.system.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserMapper.class, OrderItemMapper.class}
)
public interface OrderMapper {

    OrderResponse toOrderResponse(Order order);
}
