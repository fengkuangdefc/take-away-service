package com.chenkesi.restaurant.dto.entity;

import com.chenkesi.restaurant.enums.RestaurantStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Restaurant {
    private Integer id;
    private String name;
    private String address;
    private RestaurantStatus status;
    private Date date;
}
