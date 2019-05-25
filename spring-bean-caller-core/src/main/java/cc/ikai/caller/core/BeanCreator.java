package cc.ikai.caller.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhangjikai
 * Created on 2019-04-07
 */
@Component
public class BeanCreator implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    public static <T> T create(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
