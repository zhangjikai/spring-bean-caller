package cc.ikai.caller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Jikai Zhang
 * @date 2019-06-07
 */
public class ReflectionUtils {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
    
    public static Object directSetField(Class<?> clazz, Map<String, Object> valuesMap) {
        Objenesis objenesis = new ObjenesisStd();
        Object target = objenesis.newInstance(clazz);
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            String name = field.getName();
            if (valuesMap.containsKey(name)) {
                setFiledValue(field, target, valuesMap.get(name));
            }
        });
        return target;
    }
    
    private static void setFiledValue(Field field, Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            logger.error("", e);
            throw new RuntimeException("Failed set filed value.");
        }
    }
}
