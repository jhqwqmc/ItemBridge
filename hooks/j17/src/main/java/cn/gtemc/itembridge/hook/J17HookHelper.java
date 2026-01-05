package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.util.Utils;
import cn.gtemc.itembridge.hook.provider.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static cn.gtemc.itembridge.hook.HookHelper.tryHook;

@SuppressWarnings("unused")
public final class J17HookHelper {

    static {
        HookHelper.j17ProvidersGetter = J17HookHelper::getSupportedPlugins;
    }

    private J17HookHelper() {}

    public static void init() {
    }

    private static Map<String, Provider<ItemStack, Player>> getSupportedPlugins(
            Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure, Predicate<Plugin> filter
    ) {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>();
        tryHook(() -> Utils.addToMap(CustomFishingProvider.INSTANCE, providers), "CustomFishing", onSuccess, onFailure, filter);
        return providers;
    }
}
