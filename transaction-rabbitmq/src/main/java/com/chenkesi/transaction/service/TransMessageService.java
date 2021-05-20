package com.chenkesi.transaction.service;

import com.chenkesi.transaction.domain.dto.TransMessageDto;
import com.chenkesi.transaction.domain.entity.TransMessage;

import java.util.List;

public interface TransMessageService {
    /**
     * 保存消息
     */
    TransMessage saveMessage(TransMessageDto transMessageDto);

    /**
     * 删除消息
     */
    void deleteMessage(String id);

    /**
     * 查询应发未发消息
     */
    List<TransMessage> selectMessageList();

    /**
     * 记录消息发送次数
     * @param id id
     */
    void saveMessageResendNum(String id);

    /**
     * 消息重发多次，放弃(将消息类型转换为死信)
     * @param id id
     */
    void updateMessageByType(String id);
}
