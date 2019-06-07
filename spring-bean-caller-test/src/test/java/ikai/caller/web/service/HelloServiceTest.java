package ikai.caller.web.service;

import cc.ikai.caller.core.Caller;
import ikai.caller.web.Application;
import ikai.caller.web.service.HelloService.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

import static com.alibaba.fastjson.JSON.toJSONString;
import static org.junit.Assert.assertEquals;

/**
 * @author Jikai Zhang
 * @date 2019-06-07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class HelloServiceTest {
    
    @Autowired
    private Caller caller;
    
    private final Map<String, Object> requestMap = new HashMap<>();
    
    @Before
    public void init() {
        requestMap.put("className", "ikai.caller.web.service.HelloService");
        requestMap.put("methodName", "sayHello");
    }
    
    @Test
    public void testStringParam() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("s", "aaa");
        requestMap.put("params", paramsMap);
        assertEquals("aaa", caller.doCall(requestMap));
    }
    
    @Test
    public void testMapParam() {
        Map<String, Object> paramsMap = new HashMap<>();
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("a", 1234);
        testMap.put("b", "di");
        paramsMap.put("map", testMap);
        requestMap.put("params", paramsMap);
        assertEquals(toJSONString(testMap), caller.doCall(requestMap));
    }
    
    @Test
    public void testArrayParam() {
        Map<String, Object> paramsMap = new HashMap<>();
        String[] texts = {"1", "2", "3"};
        paramsMap.put("texts", texts);
        requestMap.put("params", paramsMap);
        assertEquals(toJSONString(texts), caller.doCall(requestMap));
    }
    
    @Test
    public void testObjectParam() {
        Map<String, Object> paramsMap = new HashMap<>();
        User user = new User();
        user.setName("a");
        user.setAge(20);
        paramsMap.put("user", user);
        requestMap.put("params", paramsMap);
        assertEquals(user.toString(), caller.doCall(requestMap));
    }
}
