package com.chenkesi.order.transaction.listener;

import com.chenkesi.order.transaction.domain.dto.TransMessageDto;
import com.chenkesi.order.transaction.domain.entity.TransMessage;
import com.chenkesi.order.transaction.service.TransMessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
public abstract class AbstractMessageListener implements ChannelAwareMessageListener {

    @Resource
    private TransMessageService transMessageService;
    @Value("${transaction.rabbitmq.resendTimes}")
    Integer resendTimes;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //将消息存盘
        MessageProperties messageProperties = message.getMessageProperties();
        TransMessage transMessage = null;
        try {
            TransMessageDto transMessageDto = new TransMessageDto();
            transMessageDto.setId(messageProperties.getMessageId());
            transMessageDto.setExchange(messageProperties.getReceivedExchange());
            transMessageDto.setRoutingKey(messageProperties.getReceivedRoutingKey());
            transMessageDto.setBody(new String(message.getBody()));
            transMessageDto.setQueue(messageProperties.getConsumerQueue());
            transMessage = transMessageService.saveReceiveMessage(transMessageDto);
            receiveMessage(message);
            channel.basicAck(messageProperties.getDeliveryTag(),false);
            transMessageService.deleteMessage(messageProperties.getMessageId());
        }catch (Exception exception){
            log.error(exception.getMessage(), exception);
            if (!Objects.isNull(transMessage) && transMessage.getSequence() >= resendTimes){
                channel.basicReject(messageProperties.getDeliveryTag(),false);
            }else {
                Thread.sleep((long)(Math.pow(2, transMessage.getSequence())) * 1000);
                channel.basicNack(messageProperties.getDeliveryTag(),false,true);
            }
        }

    }

    //由字方法来重写
    public abstract void receiveMessage(Message message);
}
