package com.chenkesi.securityframework.service;

import com.alibaba.fastjson.JSONObject;
import com.chenkesi.securityframework.domain.vo.UserVo;

public interface SecurityService {

    public JSONObject selectByName(String name);

    public JSONObject addUser(UserVo userVo);

}
