package com.ramennsama.springboot.system.mapper;

import com.ramennsama.springboot.system.dto.request.ProductRequest;
import com.ramennsama.springboot.system.dto.response.ProductResponse;
import com.ramennsama.springboot.system.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class}
)
public interface ProductMapper {

    ProductResponse toProductResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Product toProduct(ProductRequest productRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductRequest productRequest);
}
