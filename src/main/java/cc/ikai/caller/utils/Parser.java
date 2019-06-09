package cc.ikai.caller.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;


/**
 * @author zhangjikai
 * Created on 2019-04-07
 */
public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    @Nullable
    public static Method parse(String className, String methodName, Map<String, Object> paramsMap) {
        Class clazz = getClass(className);
        Method[] methods = clazz.getDeclaredMethods();
        return Arrays.stream(methods)
                .filter(m -> StringUtils.equals(methodName, m.getName()))
                .filter(m -> isTargetMethod(m, paramsMap))
                .findFirst()
                .orElse(null);
    }
    
    
    // 如果两个方法含有相同的参数名和个数，返回第一个匹配的
    private static boolean isTargetMethod(Method m, Map<String, Object> paramsMap) {
        Parameter[] parameters = m.getParameters();
        if (parameters.length != paramsMap.size()) {
            return false;
        }
        Set<String> paramNameSet = paramsMap.keySet();
        return Arrays.stream(parameters).map(Parameter::getName).allMatch(paramNameSet::contains);
    }

    public static Class getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error("", e);
            throw new RuntimeException("Class not found: " + className);
        }
    }
}
