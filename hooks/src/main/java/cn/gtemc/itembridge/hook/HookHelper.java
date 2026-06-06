package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class HookHelper {
    private static final boolean DEBUG_MODE = Boolean.getBoolean("cn{}gtemc{}itembridge{}hook{}debug".replace("{}", "."));

    private HookHelper() {}

    public static Map<String, Provider<ItemStack, Player>> getSupportedPlugins(
            @Nullable Consumer<String> onSuccess,
            @Nullable BiConsumer<String, Throwable> onFailure,
            @Nullable Predicate<Plugin> filter
    ) {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>();
        tryHook(() -> Utils.addToMap(CraftEngineProvider.INSTANCE, providers), "CraftEngine", CraftEngineProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(NexoProvider.INSTANCE, providers), "Nexo", NexoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(OraxenProvider.INSTANCE, providers), "Oraxen", OraxenProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(NovaProvider.INSTANCE, providers), "Nova", NovaProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(MythicMobsProvider.INSTANCE, providers), "MythicMobs", MythicMobsProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecoarmor"), providers), "EcoArmor", EcoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecocrates"), providers), "EcoCrates", EcoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecoitems"), providers), "EcoItems", EcoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecomobs"), providers), "EcoMobs", EcoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecopets"), providers), "EcoPets", EcoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecoscrolls"), providers), "EcoScrolls", EcoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("reforges"), providers), "Reforges", EcoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("stattrackers"), providers), "StatTrackers", EcoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("talismans"), providers), "Talismans", EcoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(HMCCosmeticsProvider.INSTANCE, providers), "HMCCosmetics", HMCCosmeticsProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(SertralineProvider.INSTANCE, providers), "Sertraline", SertralineProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(MMOItemsProvider.INSTANCE, providers), "MMOItems", MMOItemsProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(CustomFishingProvider.INSTANCE, providers), "CustomFishing", CustomFishingProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(ItemsAdderProvider.INSTANCE, providers), "ItemsAdder", ItemsAdderProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(NeigeItemsProvider.INSTANCE, providers), "NeigeItems", NeigeItemsProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(SXItemProvider.INSTANCE, providers), "SX-Item", SXItemProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(ZaphkielProvider.INSTANCE, providers), "Zaphkiel", ZaphkielProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(SlimefunProvider.INSTANCE, providers), "Slimefun", SlimefunProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(HeadDatabaseProvider.INSTANCE, providers), "HeadDatabase", HeadDatabaseProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(ExecutableItemsProvider.INSTANCE, providers), "ExecutableItems", ExecutableItemsProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(AzureFlowProvider.INSTANCE, providers), "AzureFlow", AzureFlowProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(MagicGemProvider.INSTANCE, providers), "MagicGem", MagicGemProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(PxRpgProvider.INSTANCE, providers), "PxRpg", PxRpgProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(RatzielProvider.INSTANCE, providers), "Ratziel", RatzielProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(BaikirutoProvider.INSTANCE, providers), "Baikiruto", BaikirutoProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(DragonArmourersProvider.INSTANCE, providers), "DragonArmourers", DragonArmourersProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(CrazyVouchersProvider.INSTANCE, providers), "CrazyVouchers", CrazyVouchersProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(ExecutableBlocksProvider.INSTANCE, providers), "ExecutableBlocks", ExecutableBlocksProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(ItemsXLProvider.INSTANCE, providers), "ItemsXL", ItemsXLProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(AdvancedItemsProvider.INSTANCE, providers), "AdvancedItems", AdvancedItemsProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(CustomCraftingProvider.INSTANCE, providers), "CustomCrafting", CustomCraftingProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(ItemEditProvider.INSTANCE, providers), "ItemEdit", ItemEditProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(EmakiItemProvider.INSTANCE, providers), "EmakiItem", EmakiItemProvider.Check::conflictCheck, onSuccess, onFailure, filter);
        return providers;
    }

    private static void tryHook(
            ThrowableRunnable runnable,
            String pluginName,
            @NotNull Predicate<Plugin> conflictCheck,
            @Nullable Consumer<String> onSuccess,
            @Nullable BiConsumer<String, Throwable> onFailure,
            @Nullable Predicate<Plugin> filter
    ) {
        try {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if (plugin == null || (filter != null && !filter.test(plugin))) {
                return;
            }
            if (!conflictCheck.test(plugin)) {
                if (DEBUG_MODE) System.err.println("[ItemBridge-DEBUG] Plugin " + pluginName + " failed the conflict check");
                return;
            }
            runnable.run();
            if (onSuccess != null) {
                onSuccess.accept(pluginName);
            }
        } catch (Throwable e) {
            if (onFailure != null) {
                onFailure.accept(pluginName, e);
            }
        }
    }

    @FunctionalInterface
    private interface ThrowableRunnable {

        void run() throws Throwable;
    }
}
