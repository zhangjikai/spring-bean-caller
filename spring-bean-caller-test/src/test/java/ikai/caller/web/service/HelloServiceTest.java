package ikai.caller.web.service;

import cc.ikai.caller.core.Caller;
import com.alibaba.fastjson.JSON;
import ikai.caller.web.Application;
import ikai.caller.web.service.HelloService.Product;
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
    public void testListParam() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("list", JSON.parse("['1', '2', '3']"));
        requestMap.put("params", paramsMap);
        assertEquals(paramsMap.get("list").toString(), caller.doCall(requestMap));
    }
    
    @Test
    public void testObjectParam() {
        Map<String, Object> paramsMap = new HashMap<>();
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("name", "aaa");
        testMap.put("age", 20);
        paramsMap.put("user", testMap);
        requestMap.put("params", paramsMap);
        User user = new User();
        user.setName("aaa");
        user.setAge(20);
        assertEquals(user.toString(), caller.doCall(requestMap));
    }
    
    @Test
    public void testBuilderObjectParam() {
        Map<String, Object> paramsMap = new HashMap<>();
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("name", "aaa");
        testMap.put("price", 10.01);
        paramsMap.put("product", testMap);
        requestMap.put("params", paramsMap);
        requestMap.put("directSetFiledForObjectParam", true);
        Product product = Product.newBuilder()
                .setName("aaa")
                .setPrice(10.01)
                .build();
        assertEquals(product.toString(), caller.doCall(requestMap));
    }
}
