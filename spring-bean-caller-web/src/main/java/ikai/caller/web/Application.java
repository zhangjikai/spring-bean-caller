package ikai.caller.web;

import cc.ikai.caller.config.CallerPackageScanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author zhangjikai
 * Created on 2019-04-07
 */
@SpringBootApplication
@Import(CallerPackageScanConfig.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
