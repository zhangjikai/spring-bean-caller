package cc.ikai.caller.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangjikai
 * Created on 2019-04-07
 */
@SpringBootApplication
@ComponentScan("cc.ikai.caller")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
