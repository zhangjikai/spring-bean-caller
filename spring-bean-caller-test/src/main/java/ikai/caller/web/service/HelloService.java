package ikai.caller.web.service;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * @author zhangjikai
 * Created on 2019-04-07
 */
@Service
public class HelloService {
    private final Logger logger = LoggerFactory.getLogger(HelloService.class);
    
    public String sayHello(String s) {
        return "hello: " + s;
    }

    public String sayHello(Map<String, Object> map) {
        logger.info("map: {}", map);
        return "hello: " + map;
    }

    public String sayHello(String[] texts) {
        logger.info("texts: {}", reflectionToString(texts, SHORT_PREFIX_STYLE));
        return "hello: " + reflectionToString(texts, SHORT_PREFIX_STYLE);
    }
    
    public String sayHello(User user) {
        logger.info("user {}", user);
        return "hello: " + user;
    }
    
    public static class User {
        private String name;
        private int age;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public int getAge() {
            return age;
        }
    
        public void setAge(int age) {
            this.age = age;
        }
    
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
        }
    }
}
