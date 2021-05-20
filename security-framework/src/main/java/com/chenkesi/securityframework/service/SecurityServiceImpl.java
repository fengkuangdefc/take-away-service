package com.chenkesi.securityframework.service;

import com.alibaba.fastjson.JSONObject;
import com.chenkesi.securityframework.domain.entity.User;
import com.chenkesi.securityframework.domain.vo.UserVo;
import com.chenkesi.securityframework.repository.UserRepository;
import com.lambdaworks.crypto.SCrypt;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.bind.SchemaOutputResolver;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService{

    @Resource
    private UserRepository userRepository;

    @Override
    public JSONObject selectByName(String name) {
        List<User> data = userRepository.findByName(name);
        JSONObject result = new JSONObject();
        result.put("code","200");
        result.put("message","成功");
        result.put("data",data);
        return result;
    }

    @Override
    public JSONObject addUser(UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo,user);
        final String scrypt = SCryptUtil.scrypt(userVo.getPassword(), 1024, 8, 1);
        System.out.println(scrypt);
        user.setPassword(SCryptUtil.scrypt(userVo.getPassword(),1024,8,1));
        userRepository.save(user);
        JSONObject result = new JSONObject();
        result.put("code","200");
        result.put("message","成功");
        result.put("data",new JSONObject());
        return result;
    }
}
