package cc.ikai.caller.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cc.ikai.caller.core.Parser.parse;
import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;
import static org.apache.commons.beanutils.ConvertUtils.convert;
import static org.apache.commons.collections4.MapUtils.getMap;
import static org.apache.commons.collections4.MapUtils.getString;

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
        Map<String, Object> paramsMap = (Map<String, Object>) getMap(requestMap, "params");
        Method method = parse(className, methodName, paramsMap);
        if (method == null) {
            return "Call failed: no method find!";
        }
        Object targetBean = BeanContext.get(Parser.getClass(className));
        Parameter[] parameters = method.getParameters();
        List<Object> inputValues = new ArrayList<>();
        Arrays.stream(parameters).forEach(p -> {
            Object rawValue = paramsMap.get(p.getName());
            Object convertValue;
            if (rawValue instanceof Map) {
                convertValue = parseObject(toJSONString(rawValue), p.getType());
            } else {
                convertValue = convert(rawValue, p.getType());
            }
            inputValues.add(convertValue);
        });
        try {
            return method.invoke(targetBean, inputValues.toArray(new Object[0]));
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("", e);
            return "Call failed: occur error!";
        }
    }
}
