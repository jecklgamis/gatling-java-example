package gatling.test.example.simulation;

public final class SystemPropertiesUtil {
    public static String getAsStringOrElse(String key, String fallback) {
        Object value = System.getProperty(key);
        if (value == null) {
            return fallback;
        }
        return (String) value;
    }

    public static double getAsDoubleOrElse(String key, double fallback) {
        Object value = System.getProperty(key);
        if (value == null) {
            return fallback;
        }
        return Double.parseDouble((String) value);
    }

    public static int getAsIntOrElse(String key, int fallback) {
        Object value = System.getProperty(key);
        if (value == null) {
            return fallback;
        }
        return Integer.parseInt((String) value);
    }

    public static long getAsLongOrElse(String key, long fallback) {
        Object value = System.getProperty(key);
        if (value == null) {
            return fallback;
        }
        return Long.parseLong((String) value);
    }


    public static boolean getAsBooleanOrElse(String key, boolean fallback) {
        Object value = System.getProperty(key);
        if (value == null) {
            return fallback;
        }
        return Boolean.parseBoolean((String) value);
    }
}
