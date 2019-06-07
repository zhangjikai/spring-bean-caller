package cc.ikai.caller.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;

/**
 * @author Jikai Zhang
 * @date 2019-06-07
 */
public class ReflectionUtilsTest {
    
    @Test
    public void testDirectSetFiled() {
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("name", "aaa");
        valueMap.put("age", 20);
        valueMap.put("addresses", singletonList("bbb123"));
        User user = (User) ReflectionUtils.directSetField(User.class, valueMap);
        User user2 = new User("aaa", 20, singletonList("bbb123"));
        assertEquals(user.toString(), user2.toString());
    }
    
    private static final class User {
        private String name;
        private int age;
        private List<String> addresses;
    
        public User(String name, int age, List<String> addresses) {
            this.name = name;
            this.age = age;
            this.addresses = addresses;
        }
    
        @Override
        public String toString() {
            return reflectionToString(this, SHORT_PREFIX_STYLE);
        }
    }
}
