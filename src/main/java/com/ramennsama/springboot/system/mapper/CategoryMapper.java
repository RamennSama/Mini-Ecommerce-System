package com.ramennsama.springboot.system.mapper;

import com.ramennsama.springboot.system.dto.request.CategoryRequest;
import com.ramennsama.springboot.system.dto.response.CategoryResponse;
import com.ramennsama.springboot.system.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryMapper {

    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toCategory(CategoryRequest categoryRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryRequest categoryRequest);
}
