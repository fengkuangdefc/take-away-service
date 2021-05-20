package com.chenkesi.restaurant.service;

import com.chenkesi.restaurant.dto.entity.Product;
import com.chenkesi.restaurant.dto.entity.Restaurant;
import com.chenkesi.restaurant.dto.mapper.ProductMapper;
import com.chenkesi.restaurant.dto.mapper.RestaurantMapper;
import com.chenkesi.restaurant.enums.ProductStatus;
import com.chenkesi.restaurant.enums.RestaurantStatus;
import com.chenkesi.restaurant.pojo.dto.OrderMessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class OrderMessageService {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RestaurantMapper restaurantMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;

    /*public void createQueueHandleMessage(byte[] message) throws IOException, TimeoutException, InterruptedException {
        log.info("start linstening message");
        String messageBody = new String(message);
        log.info("deliverCallback:messageBody:{}", messageBody);
        try {
            OrderMessageDTO orderMessageDTO = objectMapper.readValue(messageBody,
                    OrderMessageDTO.class);
            Product product = productMapper.selsctProduct(orderMessageDTO.getProductId());
            log.info("onMessage:productPO:{}", product);
            Restaurant restaurant = restaurantMapper.selsctRestaurant(product.getRestaurantId());
            log.info("onMessage:restaurantPO:{}", restaurant);
            if (ProductStatus.AVALIABLE == product.getStatus() && RestaurantStatus.OPEN == restaurant.getStatus()) {
                orderMessageDTO.setConfirmed(true);
                orderMessageDTO.setPrice(product.getPrice());
            } else {
                orderMessageDTO.setConfirmed(false);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }*/

    public void createQueueHandleMessage(OrderMessageDTO orderMessageDTO) throws IOException, TimeoutException, InterruptedException {
        log.info("start linstening message");
        log.info("deliverCallback:messageBody:{}", orderMessageDTO);
        Product product = productMapper.selsctProduct(orderMessageDTO.getProductId());
        log.info("onMessage:productPO:{}", product);
        Restaurant restaurant = restaurantMapper.selsctRestaurant(product.getRestaurantId());
        log.info("onMessage:restaurantPO:{}", restaurant);
        if (ProductStatus.AVALIABLE == product.getStatus() && RestaurantStatus.OPEN == restaurant.getStatus()) {
            orderMessageDTO.setConfirmed(true);
            orderMessageDTO.setPrice(product.getPrice());
        } else {
            orderMessageDTO.setConfirmed(false);
        }
    }
}

