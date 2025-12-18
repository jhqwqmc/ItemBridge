package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.context.ContextKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.neige.neigeitems.manager.ItemManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class NeigeItemsProvider implements Provider<ItemStack, Player> {
    public static final NeigeItemsProvider INSTANCE = new NeigeItemsProvider();

    private NeigeItemsProvider() {}

    @Override
    public String plugin() {
        return "neigeitems";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        Map<ContextKey<?>, Supplier<Object>> contextData = context.contextData();
        if (contextData.isEmpty()) {
            return Optional.ofNullable(ItemManager.INSTANCE.getItemStack(id, player));
        }
        return Optional.ofNullable(ItemManager.INSTANCE.getItemStack(id, player, adapt(contextData)));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        Map<ContextKey<?>, Supplier<Object>> contextData = context.contextData();
        if (contextData.isEmpty()) {
            return ItemManager.INSTANCE.getItemStack(id, player);
        }
        return ItemManager.INSTANCE.getItemStack(id, player, adapt(contextData));
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(ItemManager.INSTANCE.getItemId(item));
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return ItemManager.INSTANCE.getItemId(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return ItemManager.INSTANCE.isNiItem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return ItemManager.INSTANCE.hasItem(id);
    }

    private static Map<String, String> adapt(Map<ContextKey<?>, Supplier<Object>> contextData) {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<ContextKey<?>, Supplier<Object>> entry : contextData.entrySet()) {
            params.put(entry.getKey().key(), String.valueOf(entry.getValue().get()));
        }
        return params;
    }
}
