package cn.gtemc.itembridge.api.util;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.context.ContextKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class MiscUtils {

    private MiscUtils() {}

    public static <T, P> void addToMap(Provider<T, P> provider, Map<String, Provider<T, P>> map) {
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

    @Nullable
    public static Map<String, String> adaptString2String(BuildContext context) {
        Map<ContextKey<?>, Supplier<Object>> contextData = context.contextData();
        if (contextData.isEmpty()) {
            return null;
        }
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<ContextKey<?>, Supplier<Object>> entry : contextData.entrySet()) {
            params.put(entry.getKey().key(), String.valueOf(entry.getValue().get()));
        }
        return params;
    }

    @Nullable
    public static Map<String, Object> adaptString2Object(BuildContext context) {
        Map<ContextKey<?>, Supplier<Object>> contextData = context.contextData();
        if (contextData.isEmpty()) {
            return null;
        }
        Map<String, Object> params = new HashMap<>();
        for (Map.Entry<ContextKey<?>, Supplier<Object>> entry : contextData.entrySet()) {
            params.put(entry.getKey().key(), entry.getValue().get());
        }
        return params;
    }

    @NotNull
    public static Object[] adaptObjectArray(BuildContext context) {
        Map<ContextKey<?>, Supplier<Object>> contextData = context.contextData();
        if (contextData.isEmpty()) {
            return new Object[0];
        }
        Object[] params = new Object[contextData.size()];
        int i = 0;
        for (Map.Entry<ContextKey<?>, Supplier<Object>> entry : contextData.entrySet()) {
            params[i++] = entry.getValue().get();
        }
        return params;
    }
}
