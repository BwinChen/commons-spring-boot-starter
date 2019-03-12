package com.bwin.commons.beanutils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BeanUtilsTest {

    @Test
    public void testCopyProperties() {
        try {
            Teacher teacher = new Teacher("1", "name", 30, Arrays.asList("tv", "sport"));
            Student student = new Student();
            BeanUtils.copyProperties(student, teacher);
            log.info(student.toString());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBean2Map() {
        Teacher teacher = new Teacher("1", "name", 30, Arrays.asList("tv", "sport"));
        Map map = (Map)JSON.parse(JSON.toJSONString(teacher));
        log.info(map.toString());
    }

    @Test
    public void testMap2Bean() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id" , "1");
            map.put("name", "name");
            map.put("age", 30);
            map.put("hobbies", Arrays.asList("tv", "sport"));
            Teacher teacher = new Teacher();
            BeanUtils.populate(teacher, map);
            log.info(teacher.toString());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBean2Map2() {
        Teacher teacher = new Teacher("1", "name", 30, Arrays.asList("tv", "sport"));
        Map<String, Object> map = bean2Map(teacher);
        log.info(map.toString());
    }

    public static Map<String, Object> bean2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 获取javaBean的BeanInfo对象
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
            // 获取属性描述器
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                // 获取属性名
                String key = propertyDescriptor.getName();
                // 获取该属性的值
                Method readMethod = propertyDescriptor.getReadMethod();
                // 通过反射来调用javaBean定义的getName()方法
                Object value = readMethod.invoke(obj);
                map.put(key, value);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return map;
    }

    public static <T> T map2Bean(Map<String, Object> map , Class<T> clazz) throws Exception{
        // new 出一个对象
        T obj = clazz.newInstance();
        // 获取javaBean的BeanInfo对象
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
        // 获取属性描述器
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            // 获取属性名
            String key = propertyDescriptor.getName();
            Object value = map.get(key);
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (map.containsKey(key)){
                // 解决 argument type mismatch 的问题，转换成对应的javaBean属性类型
                String typeName = propertyDescriptor.getPropertyType().getTypeName();
                // System.out.println(key +"<==>"+ typeName);
                if ("java.lang.Integer".equals(typeName)){
                    value = Integer.parseInt(value.toString());
                }
                if ("java.lang.Long".equals(typeName)){
                    value = Long.parseLong(value.toString());
                }
                if ("java.util.Date".equals(typeName)){
                    value = new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
                }
            }
            // 通过反射来调用javaBean定义的setName()方法
            writeMethod.invoke(obj,value);
        }
        return obj;
    }

}
