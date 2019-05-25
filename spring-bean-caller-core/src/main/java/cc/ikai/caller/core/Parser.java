package cc.ikai.caller.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhangjikai
 * Created on 2019-04-07
 */
public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    public static void parseRequest(Map<String, Object> requestMap) {

    }

    public static void parseClass(String className, String methodName) {
        Class clazz = getClass(className);
        Method[] methods = clazz.getDeclaredMethods();
        for(Method m : methods) {
            if (StringUtils.equals(methodName, m.getName())) {
                Parameter[] parameters = m.getParameters();
                for(Parameter p: parameters) {
                    System.out.println(p.getName());
                }
                logger.info("parameters {}", parameters);
            }
        }
    }

    private static Class getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
