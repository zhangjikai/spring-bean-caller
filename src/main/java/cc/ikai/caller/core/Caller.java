package cc.ikai.caller.core;

import cc.ikai.caller.utils.Parser;
import cc.ikai.caller.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import static cc.ikai.caller.utils.Parser.parse;
import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;
import static java.util.Optional.ofNullable;
import static org.apache.commons.beanutils.ConvertUtils.convert;
import static org.apache.commons.collections4.MapUtils.*;

/**
 * @author Jikai Zhang
 * @date 2019-05-26
 */
@Service
public class Caller {
    
    private final Logger logger = LoggerFactory.getLogger(Caller.class);
    
    @SuppressWarnings("unchecked")
    public Object doCall(Map<String, Object> requestMap) {
        String className = getString(requestMap, "className");
        String methodName = getString(requestMap, "methodName");
        boolean isDirectSetFiled = ofNullable(getBoolean(requestMap, "directSetFiledForObjectParam")).orElse(false);
        Map<String, Object> paramsMap = (Map<String, Object>) getMap(requestMap, "params");
        Method method = parse(className, methodName, paramsMap);
        if (method == null) {
            return "Call failed: no method find!";
        }
        Object targetBean = BeanContext.get(Parser.getClass(className));
        Parameter[] parameters = method.getParameters();
        List<Object> inputValues = new ArrayList<>();
        Arrays.stream(parameters).forEach(p -> {
            Object convertValue = getConvertedParam(p, paramsMap, isDirectSetFiled);
            inputValues.add(convertValue);
        });
        try {
            return method.invoke(targetBean, inputValues.toArray(new Object[0]));
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("", e);
            return "Call failed: occur error!";
        }
    }
    
    @SuppressWarnings("unchecked")
    private Object getConvertedParam(Parameter p, Map<String, Object> paramsMap, boolean isDirectSetField) {
        Object rawValue = paramsMap.get(p.getName());
        Class paramClass = p.getType();
        if (isEnumParam(paramClass)) {
            return Enum.valueOf(paramClass, Objects.toString(paramsMap.get(p.getName())));
        }
        if (!(rawValue instanceof Map)) {
            return convert(rawValue, p.getType());
        }
        if (isCollectionParam(paramClass) || !isDirectSetField) {
            return parseObject(toJSONString(rawValue), p.getType());
        }
        Map<String, Object> localMap = (Map<String, Object>) rawValue;
        return ReflectionUtils.directSetField(paramClass, localMap);
    }
    
    private boolean isCollectionParam(Class paramClass) {
        return Map.class.isAssignableFrom(paramClass) || Collection.class.isAssignableFrom(paramClass);
    }
    
    private boolean isEnumParam(Class paramClass) {
        return paramClass.isEnum();
    }
}
