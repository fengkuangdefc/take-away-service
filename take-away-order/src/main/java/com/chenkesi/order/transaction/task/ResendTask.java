package com.chenkesi.order.transaction.task;

import com.alibaba.fastjson.JSON;
import com.chenkesi.order.transaction.domain.entity.TransMessage;
import com.chenkesi.order.transaction.enums.TransMessageType;
import com.chenkesi.order.transaction.service.TransMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@EnableScheduling
@Configuration
@Component
@Slf4j
public class ResendTask {

    @Value("${transaction.rabbitmq.resendTimes}")
    Integer resendTimes;
    @Resource
    private TransMessageService transMessageService;
    @Resource
    private RabbitTemplate transactionRabbitTemplate;

    @Scheduled(fixedDelayString = "10000")
    public void resendMessage(){
        //这里需要实现分布式锁 或者其他方式保证只有一个循环次队列
        log.info("resendMessage() invoked.");
        List<TransMessage> transMessageList = transMessageService.selectMessageList(TransMessageType.SEND.toString());
        log.info("resendMessage(): messagepos:{}", transMessageList);
        for (TransMessage transMessage : transMessageList) {
            //判断是否超过重发次数
            log.info("resendMessage(): transMessage:{}", transMessage);
            if (transMessage.getSequence() > resendTimes){
                log.error("resend too many times!");
                transMessageService.updateMessageByType(TransMessageType.DEAD.name(),transMessage.getId());
                continue;
            }
            //如果没有超过次数，需要进行重试流程
            MessageProperties properties = new MessageProperties();
            properties.setContentType("application/json");
            Message message = new Message(JSON.toJSONBytes(transMessage.getPayload()), properties);
            transactionRabbitTemplate.convertAndSend(
                    transMessage.getExchange(),
                    transMessage.getRoutingkey(),
                    message,
                    new CorrelationData(transMessage.getId())
            );
            log.info("message sent, ID:{}", transMessage.getId());
            transMessageService.saveMessageResendNum(transMessage.getId());
        }
    }
}
