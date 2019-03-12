package com.bwin.commons.util;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties pro = new Properties();

    static {
		//加载配置文件
        PropertiesUtil.load("classpath:my.properties");
	}

    public static void load(String path) {
        FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
        Resource resource = fileSystemResourceLoader.getResource(path);
        try {
            pro.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key, String defaultValue) {
        String value = (String) pro.get(key);
        if (value == null || "".equals(value)) {
            return defaultValue;
        }
        return value;
    }
    
    public static String getString(String key) {
        String value = (String) pro.get(key);
        return value;
    }

    public static int getInt(String key) {
        Object value = pro.get(key);
        return Integer.parseInt(value.toString());
    }
    
    public static long getLong(String key) {
        Object value = pro.get(key);
        return Long.parseLong(value.toString());
    }
    
    public static double getDouble(String key) {
        Object value = pro.get(key);
        return Double.parseDouble(value.toString());
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        Object value = pro.get(key);
        if (value == null || "".equals(value)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.toString());
    }

}
