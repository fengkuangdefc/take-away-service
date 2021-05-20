package com.chenkesi.restaurant.dto.mapper;

import com.chenkesi.restaurant.dto.entity.Restaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RestaurantMapper {

    @Select("SELECT id,name,address,status,settlement_id settlementId,date FROM restaurant WHERE id = #{id}")
    Restaurant selsctRestaurant(Integer id);
}
