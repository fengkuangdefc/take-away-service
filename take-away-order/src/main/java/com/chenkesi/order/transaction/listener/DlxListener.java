package com.chenkesi.order.transaction.listener;

import com.chenkesi.order.transaction.domain.dto.TransMessageDto;
import com.chenkesi.order.transaction.service.TransMessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@ConditionalOnProperty("transaction.rabbitmq.dlxEnabled")
public class DlxListener implements ChannelAwareMessageListener {

    @Resource
    private TransMessageService transMessageService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String messageBody = new String(message.getBody());
        log.error("dead letter! message:{}", message);
        //发邮件、打电话、发短信
        //XXXXX（）
        MessageProperties messageProperties = message.getMessageProperties();
        TransMessageDto transMessageDto = new TransMessageDto();
        transMessageDto.setId(messageProperties.getMessageId());
        transMessageDto.setExchange(messageProperties.getReceivedExchange());
        transMessageDto.setRoutingKey(messageProperties.getReceivedRoutingKey());
        transMessageDto.setQueue(messageProperties.getConsumerQueue());
        transMessageDto.setBody(messageBody);
        transMessageService.saveReceiveMessage(transMessageDto);
        channel.basicAck(messageProperties.getDeliveryTag(), false);
    }
}
