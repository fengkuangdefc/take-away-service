package com.chenkesi.order.transaction.service;

import com.chenkesi.order.transaction.domain.dto.TransMessageDto;
import com.chenkesi.order.transaction.domain.entity.TransMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class TransMessageSender {

    @Resource
    private TransMessageService transMessageService;
    @Resource
    private RabbitTemplate transactionRabbitTemplate;

    public void send(String exchange,String routingKey, Object body){
        log.info("message send：exchange:{} routingKey:{} payload:{}",
                exchange,routingKey,body);
        //将消息入库
        TransMessageDto transMessageDto = new TransMessageDto();
        transMessageDto.setRoutingKey(routingKey);
        transMessageDto.setExchange(exchange);
        String bodyString = String.valueOf(body);
        transMessageDto.setBody(String.valueOf(body));
        TransMessage transMessage = transMessageService.saveMessage(transMessageDto);
        //将消息发送大MQ 这里需要携带实体类的ID，方便后续进行消息确认
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        properties.setMessageId(transMessage.getId());
        //properties.setCorrelationId(transMessage.getId());
        CorrelationData correlationData = new CorrelationData(transMessage.getId());
        Message message = new Message(bodyString.getBytes(), properties);
        transactionRabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                message,
                correlationData
        );
        log.info("message sent, ID:{}", transMessage.getId());
    }
}
