package com.chenkesi.order.transaction.service;

import com.chenkesi.order.transaction.domain.dto.TransMessageDto;
import com.chenkesi.order.transaction.domain.entity.TransMessage;

import java.util.List;

public interface TransMessageService {
    /**
     * 保存发送消息
     */
    TransMessage saveMessage(TransMessageDto transMessageDto);

    /**
     * 删除消息
     */
    void deleteMessage(String id);

    /**
     * 查询应发未发消息
     */
    List<TransMessage> selectMessageList(String type);

    /**
     * 记录消息发送次数
     * @param id id
     */
    void saveMessageResendNum(String id);

    /**
     * 消息重发多次，放弃(将消息类型转换为死信)
     * @param id id
     */
    void updateMessageByType(String type, String id);

    /**
     * 保存接收消息
     */
    TransMessage saveReceiveMessage(TransMessageDto transMessageDto);

}
