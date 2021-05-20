package com.chenkesi.restaurant.config;

import com.chenkesi.restaurant.pojo.dto.OrderMessageDTO;
import com.chenkesi.restaurant.service.OrderMessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
@Configuration
public class RabbitConfig {

    @Autowired
    private OrderMessageService orderMessageService;

    @Value("${transaction.rabbitmq.host}")
    String host;
    @Value("${transaction.rabbitmq.port}")
    int port;
    @Value("${transaction.rabbitmq.username}")
    String username;
    @Value("${transaction.rabbitmq.password}")
    String password;
    @Value("${transaction.rabbitmq.virtualHost}")
    String virtualHost;

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("101.200.128.57");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        //开启消息确认模式
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        //开启消息return模式
        connectionFactory.setPublisherReturns(true);
        connectionFactory.createConnection();
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(@Autowired ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(@Autowired ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames("queue.order.create");
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setConcurrentConsumers(3);
        container.setMaxConcurrentConsumers(10);
        //TODO container设置限流
        container.setBatchSize(5);
        MessageListenerAdapter adapter = new MessageListenerAdapter(orderMessageService);
        Map<String,String> queueOrTagToMethodName = new HashMap<>();
        queueOrTagToMethodName.put("queue.order.create","createQueueHandleMessage");
        adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        Map<String,Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("OrderMessageDTO", OrderMessageDTO.class);
        typeMapper.setIdClassMapping(idClassMapping);
        converter.setJavaTypeMapper(typeMapper);
        adapter.setMessageConverter(converter);
        container.setMessageListener(adapter);
        return container;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(@Autowired ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {

            }
        });
        return rabbitTemplate;
    }

}
