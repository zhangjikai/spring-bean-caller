package ikai.caller.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

import static com.alibaba.fastjson.JSON.toJSONString;
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
        return s;
    }

    public String sayHello(Map<String, Object> map) {
        logger.info("map: {}", map);
        return toJSONString(map);
    }

    public String sayHello(String[] texts) {
        logger.info("texts: {}", reflectionToString(texts, SHORT_PREFIX_STYLE));
        return toJSONString(texts);
    }
    
    public String sayHello(Collection<String> list) {
        return list.toString();
    }
    public String sayHello(User user) {
        logger.info("user {}", user);
        return user.toString();
    }
    
    public String sayHello(Product product) {
        return product.toString();
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
            return reflectionToString(this, SHORT_PREFIX_STYLE);
        }
    }
    
    /**
     * Builder Pattern Object
     */
    public static class Product {
        private String name;
        private double price;
    
        private Product(Builder builder) {
            name = builder.name;
            price = builder.price;
        }

        public static Builder newBuilder() {
            return new Builder();
        }
    
        public String getName() {
            return name;
        }
    
        public double getPrice() {
            return price;
        }
    
        @Override
        public String toString() {
            return reflectionToString(this, SHORT_PREFIX_STYLE);
        }
    
        public static class Builder {
            private String name;
            private double price;

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setPrice(double price) {
                this.price = price;
                return this;
            }

            public Product build() {
                return new Product(this);
            }
        }
    }
}
