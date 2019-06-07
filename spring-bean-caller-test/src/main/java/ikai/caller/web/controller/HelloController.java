package ikai.caller.web.controller;

import cc.ikai.caller.core.Caller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjikai
 * Created on 2019-04-07
 */
@RestController
public class HelloController {

    @Autowired
    private Caller caller;
    
    @RequestMapping("invoke")
    public Map<String, Object> invoke(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", caller.doCall(requestMap));
        return resultMap;
    }
}
