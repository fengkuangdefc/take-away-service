package com.chenkesi.transaction.service;

import com.chenkesi.transaction.domain.dto.TransMessageDto;
import com.chenkesi.transaction.domain.entity.TransMessage;
import com.chenkesi.transaction.domain.entity.TransMessageExample;
import com.chenkesi.transaction.domain.entity.TransMessageKey;
import com.chenkesi.transaction.enums.TransMessageType;
import com.chenkesi.transaction.mapper.TransMessageMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
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
        BeanUtils.copyProperties(transMessageDto,transMessage);
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
    public List<TransMessage> selectMessageList() {
        return transMessageMapperService.selectByTypeAndService(
                TransMessageType.SEND.toString() ,serviceName
        );
    }

    @Override
    public void saveMessageResendNum(String id) {
        transMessageMapperService.updateByIdAndService(id, serviceName);
    }

    @Override
    public void updateMessageByType(String id) {
        transMessageMapperService.updateTypeBySequence(TransMessageType.DEAD.name(),3);
    }
}
