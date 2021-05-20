package com.chenkesi.order.transaction.service;

import com.chenkesi.order.transaction.domain.dto.TransMessageDto;
import com.chenkesi.order.transaction.domain.entity.TransMessage;
import com.chenkesi.order.transaction.enums.TransMessageType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TransMessageServiceImpl implements TransMessageService {

    @Resource
    private TransMessageMapperService transMessageMapperService;
    @Value("${transaction.rabbitmq.service}")
    String serviceName;

    @Override
    public TransMessage saveMessage(TransMessageDto transMessageDto) {
        String id = transMessageDto.getId();
        if (StringUtils.isBlank(id)){
            id = UUID.randomUUID().toString().replace("-","")
                    .substring(0,9);
            transMessageDto.setId(id);
        }
        TransMessage transMessage = new TransMessage();
        transMessage.setExchange(transMessageDto.getExchange());
        transMessage.setId(transMessageDto.getId());
        transMessage.setRoutingkey(transMessageDto.getRoutingKey());
        transMessage.setPayload(transMessageDto.getBody());
        transMessage.setService(serviceName);
        transMessage.setDate(new Date());
        transMessage.setSequence(0);
        transMessage.setType(TransMessageType.SEND.name());
        transMessageMapperService.insert(transMessage);
        return transMessage;
    }

    @Override
    public void deleteMessage(String id) {
        transMessageMapperService.deleteByPrimaryKey(id, serviceName);
    }

    @Override
    public List<TransMessage> selectMessageList(String type) {
        return transMessageMapperService.selectByTypeAndService(
                type ,serviceName
        );
    }

    @Override
    public void saveMessageResendNum(String id) {
        transMessageMapperService.updateByIdAndService(id, serviceName);
    }

    @Override
    public void updateMessageByType(String type, String id) {
        transMessageMapperService.updateTypeById(id,type);
    }

    @Override
    public TransMessage saveReceiveMessage(TransMessageDto transMessageDto) {
        TransMessage transMessage = transMessageMapperService.selectByPrimaryKey(transMessageDto.getId(), serviceName);
        if (Objects.isNull(transMessage)){
            transMessage = new TransMessage();
            transMessage.setId(transMessageDto.getId());
            transMessage.setService(serviceName);
            transMessage.setExchange(transMessageDto.getExchange());
            transMessage.setRoutingkey(transMessageDto.getRoutingKey());
            transMessage.setQueue(transMessageDto.getQueue());
            transMessage.setPayload(transMessageDto.getBody());
            transMessage.setDate(new Date());
            transMessage.setSequence(0);
            transMessage.setType(TransMessageType.RECEIVE.name());
            transMessageMapperService.insert(transMessage);
        }else {
            transMessageMapperService.updateByIdAndService(transMessageDto.getId(),serviceName);
        }
        return transMessage;
    }
}
