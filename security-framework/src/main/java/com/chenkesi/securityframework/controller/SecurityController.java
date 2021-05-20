package com.chenkesi.securityframework.controller;

import com.alibaba.fastjson.JSONObject;
import com.chenkesi.securityframework.domain.vo.UserVo;
import com.chenkesi.securityframework.service.SecurityService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @Resource
    private SecurityService securityService;

    @GetMapping("/select/{name}")
    public JSONObject selectByName(@PathVariable("name") String name){
        return securityService.selectByName(name);
    }

    @PostMapping("/add")
    public JSONObject addUser(@RequestBody @Valid UserVo userVo, BindingResult bindingResult){
        List<ObjectError> errors = bindingResult.getAllErrors();
        JSONObject result = new JSONObject();
        if (!Objects.isNull(errors) && !errors.isEmpty()){
            result.put("code","400");
            result.put("message",errors.get(0).getDefaultMessage());
            result.put("data",new JSONObject());
            return result;
        }
        JSONObject jsonObject = securityService.addUser(userVo);
        return jsonObject;
    }

}
