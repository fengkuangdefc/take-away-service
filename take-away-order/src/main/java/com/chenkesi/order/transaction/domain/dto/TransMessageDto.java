package com.chenkesi.order.transaction.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransMessageDto {
    private String id;
    private String exchange;
    private String routingKey;
    private String body;
    private String queue;
}
