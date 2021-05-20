package com.chenkesi.transaction.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TransactionRabbitConfig {

    @Value("${transaction.rabbitmq.host}")
    String host;
    @Value("${transaction.rabbitmq.port}")
    int port;
    @Value("${transaction.rabbitmq.username}")
    String username;
    @Value("${transaction.rabbitmq.password}")
    String password;
    @Value("${transaction.rabbitmq.vhost}")
    String virtualHost;

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        //开启消息确认模式
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        //开启消息return模式
        connectionFactory.setPublisherReturns(true);
        connectionFactory.createConnection();
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(@Autowired ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin  = new RabbitAdmin(connectionFactory);
        //允许自动创建队列
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 如果定义监听器需要使用@Listener注解的方式，需要声明这个类
     */
    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(@Autowired ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置连接工厂
        factory.setConnectionFactory(connectionFactory);
        //设置并发消费者数量
        factory.setConcurrentConsumers(3);
        //设置最大并发消费者数量
        factory.setMaxConcurrentConsumers(10);
        //设置签收模式为手动签收
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public RabbitTemplate transactionRabbitTemplate(@Autowired ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {

            }
        });
        //int replyCode, String replyText, String exchange, String routingKey
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

            }
        });
        return rabbitTemplate;
    }

    

}
