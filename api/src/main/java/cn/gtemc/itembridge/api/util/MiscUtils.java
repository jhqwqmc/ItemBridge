package cn.gtemc.itembridge.api.util;

import cn.gtemc.itembridge.api.Provider;

import java.util.Map;

public final class MiscUtils {

    private MiscUtils() {}

    public static boolean isRunningOnJava21() {
        try {
            int version = Runtime.version().feature();
            return version >= 21;
        } catch (Throwable e) {
            try {
                int version = Integer.parseInt(System.getProperty("java.version"));
                return version >= 21;
            } catch (Throwable t) {
                return false;
            }
        }
    }

    public static <T> void addToMap(Provider<T> provider, Map<String, Provider<T>> map) {
        map.put(provider.plugin(), provider);
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className.replace("{}", "."));
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
