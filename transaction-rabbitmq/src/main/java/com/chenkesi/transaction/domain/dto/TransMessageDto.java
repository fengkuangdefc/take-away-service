package com.chenkesi.transaction.domain.dto;

import com.chenkesi.transaction.enums.TransMessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TransMessageDto {
    private String id;
    private String exchange;
    private String routingKey;
    private String body;
}
