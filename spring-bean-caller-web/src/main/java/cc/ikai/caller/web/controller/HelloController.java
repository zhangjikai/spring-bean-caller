package cc.ikai.caller.web.controller;

import cc.ikai.caller.core.BeanCreator;
import cc.ikai.caller.core.Parser;
import cc.ikai.caller.web.service.HelloService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.apache.commons.collections4.MapUtils.getMap;
import static org.apache.commons.collections4.MapUtils.getString;

/**
 * @author zhangjikai
 * Created on 2019-04-07
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/call")
    public String call() {
        String name = "a";
        HelloService helloService = BeanCreator.create(HelloService.class);
        return helloService.sayHello(name);
    }

    @RequestMapping("invoke")
    public String invoke(@RequestBody Map<String, Object> requestMap) {
        System.out.println(requestMap.get("class"));
        String className = getString(requestMap, "className");
        String methodName = getString(requestMap, "methodName");
        Map<String, Object> paramsMap = (Map<String, Object>) getMap(requestMap, "params");
        Parser.parseClass(className, methodName);
        return "invoke";
    }
}
