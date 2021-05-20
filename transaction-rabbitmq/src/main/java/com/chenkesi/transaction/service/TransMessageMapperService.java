package com.chenkesi.transaction.service;

import com.chenkesi.transaction.domain.entity.TransMessage;
import com.chenkesi.transaction.domain.entity.TransMessageExample;
import com.chenkesi.transaction.domain.entity.TransMessageKey;
import com.chenkesi.transaction.mapper.TransMessageMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TransMessageMapperService {

    @Resource
    private TransMessageMapper transMessageMapper;

    public int deleteByPrimaryKey(String id, String serviceName){
        TransMessageKey transMessageKey = new TransMessageKey();
        transMessageKey.setId(id);
        transMessageKey.setService(serviceName);
        return transMessageMapper.deleteByPrimaryKey(transMessageKey);
    }

    public int insert(TransMessage transMessage){
        return transMessageMapper.insert(transMessage);
    }

    public List<TransMessage> selectByTypeAndService(String type, String serviceName) {
        TransMessageExample example = new TransMessageExample();
        TransMessageExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type);
        criteria.andServiceEqualTo(serviceName);
        return transMessageMapper.selectByExample(example);
    }

    public int updateByIdAndService(String id, String serviceName) {
        return transMessageMapper.updateByIdAndService(id,serviceName);
    }

    public int updateTypeBySequence(String type,int sequence) {
        return transMessageMapper.updateTypeBySequence(sequence,type);
    }
}
