package com.chenkesi.order.controller;

import com.chenkesi.order.pojo.vo.OrderCreateVO;
import com.chenkesi.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public void createOrder(@RequestBody OrderCreateVO orderCreateDTO) throws IOException, TimeoutException {
        log.info("createOrder:orderCreateDTO:{}",orderCreateDTO);
        orderService.createOrder(orderCreateDTO);
    }
}
