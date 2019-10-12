package top.eviltuzki.tools.mybatissql.util;

import java.util.HashMap;
import java.util.Map;

public class Db2JavaTypeMapping {
    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("varchar", "String");
        map.put("bigint", "Integer");
        map.put("longtext", "String");
        map.put("datetime", "Date");
        map.put("int", "Integer");
        map.put("tinyint", "Integer");
        map.put("decimal", "Double");
        map.put("double", "Double");
        map.put("date", "Date");
        map.put("text", "String");
        map.put("char", "String");
        map.put("timestamp", "Date");
        map.put("float", "Double");
        map.put("mediumtext", "String");
        map.put("smallint", "Integer");
        map.put("time", "Date");
    }

    public static String get(String type) {
        return map.get(type);
    }

}
