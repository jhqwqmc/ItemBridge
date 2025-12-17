package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.util.MiscUtils;
import cn.gtemc.itembridge.hook.provider.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static cn.gtemc.itembridge.hook.HookHelper.hasPlugin;

@SuppressWarnings("unused")
public final class J21HookHelper {

    static {
        HookHelper.j21ProvidersGetter = J21HookHelper::getSupportedPlugins;
    }

    private J21HookHelper() {}

    private static Map<String, Provider<ItemStack, Player>> getSupportedPlugins() {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>();
        if (hasPlugin("CraftEngine")) {
            MiscUtils.addToMap(CraftEngineProvider.INSTANCE, providers);
        }
        if (hasPlugin("Nexo")) {
            MiscUtils.addToMap(NexoProvider.INSTANCE, providers);
        }
        if (hasPlugin("Oraxen")) {
            MiscUtils.addToMap(OraxenProvider.INSTANCE, providers);
        }
        if (hasPlugin("Nova")) {
            MiscUtils.addToMap(NovaProvider.INSTANCE, providers);
        }
        if (hasPlugin("MythicMobs")) {
            MiscUtils.addToMap(MythicMobsProvider.INSTANCE, providers);
        }
        if (hasPlugin("EcoArmor")) {
            MiscUtils.addToMap(new EcoProvider("ecoarmor"), providers);
        }
        if (hasPlugin("EcoCrates")) {
            MiscUtils.addToMap(new EcoProvider("ecocrates"), providers);
        }
        if (hasPlugin("EcoItems")) {
            MiscUtils.addToMap(new EcoProvider("ecoitems"), providers);
        }
        if (hasPlugin("EcoMobs")) {
            MiscUtils.addToMap(new EcoProvider("ecomobs"), providers);
        }
        if (hasPlugin("EcoPets")) {
            MiscUtils.addToMap(new EcoProvider("ecopets"), providers);
        }
        if (hasPlugin("EcoScrolls")) {
            MiscUtils.addToMap(new EcoProvider("ecoscrolls"), providers);
        }
        if (hasPlugin("Reforges")) {
            MiscUtils.addToMap(new EcoProvider("reforges"), providers);
        }
        if (hasPlugin("StatTrackers")) {
            MiscUtils.addToMap(new EcoProvider("stattrackers"), providers);
        }
        if (hasPlugin("Talismans")) {
            MiscUtils.addToMap(new EcoProvider("talismans"), providers);
        }
        if (hasPlugin("HMCCosmetics")) {
            MiscUtils.addToMap(HMCCosmeticsProvider.INSTANCE, providers);
        }
        return providers;
    }
}
