package top.eviltuzki.tools.mybatissql.util;

public class ConfigUtil {
    private static String dbUrl;
    private static String originalUrl;
    private static String username;
    private static String password;

    public static String getDbUrl() {
        return dbUrl;
    }

    public static void setDbUrl(String dbUrl) {
        ConfigUtil.dbUrl = dbUrl;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ConfigUtil.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ConfigUtil.password = password;
    }

    public static void setOriginalUrl(String originalUrl) {
        ConfigUtil.originalUrl = originalUrl;

    }

    public static String getOriginalUrl() {
        return originalUrl;
    }
}
