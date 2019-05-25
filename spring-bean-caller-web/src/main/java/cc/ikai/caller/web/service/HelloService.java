package cc.ikai.caller.web.service;

import org.springframework.stereotype.Service;

/**
 * @author zhangjikai
 * Created on 2019-04-07
 */
@Service
public class HelloService {
    public String sayHello(String s) {
        return "hello: " + s;
    }
}
