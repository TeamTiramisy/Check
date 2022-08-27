package com.console.check.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.hibernate.cfg.Environment;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import static com.console.check.util.Constants.*;

@UtilityClass
public class PropertiesUtil {
    private static final Yaml YML = new Yaml();
    private static Map<String, Map<String, Object>> map;

    static {
        loadProperties();
    }

    @SneakyThrows
    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.yml")) {
            map = YML.load(inputStream);
        }
    }

    public String get(String key){
        String[] split = key.split(SEPARATOR);
        Map<String, Object> stringMap = map.get(split[0]);
        return stringMap.get(split[1]).toString();
    }

    public Properties addProperties(){
        Properties properties = new Properties();

        properties.put(Environment.SHOW_SQL, get(SHOW_KEY));
        properties.put(Environment.FORMAT_SQL, get(FORMAT_KEY));
        properties.put(Environment.DIALECT, get(DIALECT_KEY));

        return properties;
    }
}
