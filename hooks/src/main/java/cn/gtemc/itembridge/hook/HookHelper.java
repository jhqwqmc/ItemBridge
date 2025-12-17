package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.ItemBridgeException;
import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.util.MiscUtils;
import cn.gtemc.itembridge.hook.provider.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class HookHelper {
    static Supplier<Map<String, Provider<ItemStack, Player>>> j21ProvidersGetter;

    static {
        if (MiscUtils.isRunningOnJava21()) {
            try {
                MethodHandles.lookup().ensureInitialized(Class.forName("cn.gtemc.itembridge.hook.J21HookHelper"));
            } catch (ReflectiveOperationException e) {
                throw new ItemBridgeException("Failed to load builtin providers", e);
            }
        } else {
            j21ProvidersGetter = Map::of;
        }
    }

    private HookHelper() {}

    static boolean hasPlugin(String plugin) {
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }

    public static Map<String, Provider<ItemStack, Player>> getSupportedPlugins() {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>(j21ProvidersGetter.get());
        if (hasPlugin("CustomFishing")) {
            MiscUtils.addToMap(CustomFishingProvider.INSTANCE, providers);
        }
        if (hasPlugin("ItemsAdder")) {
            MiscUtils.addToMap(ItemsAdderProvider.INSTANCE, providers);
        }
        if (hasPlugin("MMOItems")) {
            MiscUtils.addToMap(MMOItemsProvider.INSTANCE, providers);
        }
        if (hasPlugin("NeigeItems")) {
            MiscUtils.addToMap(NeigeItemsProvider.INSTANCE, providers);
        }
        if (hasPlugin("SX-Item")) {
            MiscUtils.addToMap(SXItemProvider.INSTANCE, providers);
        }
        if (hasPlugin("Zaphkiel")) {
            MiscUtils.addToMap(ZaphkielProvider.INSTANCE, providers);
        }
        if (hasPlugin("Slimefun")) {
            MiscUtils.addToMap(SlimefunProvider.INSTANCE, providers);
        }
        if (hasPlugin("HeadDatabase")) {
            MiscUtils.addToMap(HeadDatabaseProvider.INSTANCE, providers);
        }
        if (hasPlugin("ExecutableItems")) {
            MiscUtils.addToMap(ExecutableItemsProvider.INSTANCE, providers);
        }
        if (hasPlugin("AzureFlow")) {
            MiscUtils.addToMap(AzureFlowProvider.INSTANCE, providers);
        }
        if (hasPlugin("MagicGem")) {
            MiscUtils.addToMap(MagicGemProvider.INSTANCE, providers);
        }
        if (hasPlugin("PxRpg")) {
            MiscUtils.addToMap(PxRpgProvider.INSTANCE, providers);
        }
        if (hasPlugin("Ratziel")) {
            MiscUtils.addToMap(RatzielProvider.INSTANCE, providers);
        }
        return providers;
    }

}