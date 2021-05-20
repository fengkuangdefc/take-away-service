package com.chenkesi.order.service;

import com.alibaba.fastjson.JSON;
import com.chenkesi.order.dao.entity.OrderDetail;
import com.chenkesi.order.dao.mapper.OrderDetailMapper;
import com.chenkesi.order.enums.OrderStatus;
import com.chenkesi.order.pojo.dto.OrderMessageDTO;
import com.chenkesi.order.pojo.vo.OrderCreateVO;
import com.chenkesi.order.transaction.service.TransMessageSender;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class OrderService {

    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private TransMessageSender transMessageSender;

    public void createOrder(OrderCreateVO orderCreateVO) throws IOException, TimeoutException {
        log.info("createOrder:orderCreateVO:{}", orderCreateVO);
        OrderDetail orderPO = new OrderDetail();
        orderPO.setAddress(orderCreateVO.getAddress());
        orderPO.setAccountId(orderCreateVO.getAccountId());
        orderPO.setProductId(orderCreateVO.getProductId());
        orderPO.setStatus(OrderStatus.ORDER_CREATING);
        orderPO.setDate(new Date());
        orderDetailMapper.insert(orderPO);
        OrderMessageDTO orderMessageDTO = new OrderMessageDTO();
        orderMessageDTO.setOrderId(orderPO.getId());
        orderMessageDTO.setProductId(orderPO.getProductId());
        orderMessageDTO.setAccountId(orderCreateVO.getAccountId());
        //向MQ发送消息
        transMessageSender.send(
                "exchange.order.restaurant.create",
                "key.restaurant.create1",
                JSON.toJSONString(orderMessageDTO)
        );
        /*ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("101.200.128.57");
        connectionFactory.setPort(5672);
        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()){
            String messageBody = JSON.toJSONString(orderMessageDTO);
            channel.basicPublish(
                    "exchange.order.restaurant.create",
                    "key.restaurant.create",
                    null,
                    messageBody.getBytes()
            );
        }*/
    }

}
