package com.chenkesi.restaurant.dto.mapper;

import com.chenkesi.restaurant.dto.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductMapper {

    @Select("SELECT id,name,price,restaurant_id restaurantId,status,date FROM product WHERE id = #{id}")
    Product selsctProduct(Integer id);
}
