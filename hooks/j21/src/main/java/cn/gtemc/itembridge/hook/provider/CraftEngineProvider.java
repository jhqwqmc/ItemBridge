package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import net.momirealms.craftengine.bukkit.api.BukkitAdaptors;
import net.momirealms.craftengine.bukkit.api.CraftEngineItems;
import net.momirealms.craftengine.core.item.CustomItem;
import net.momirealms.craftengine.core.item.ItemBuildContext;
import net.momirealms.craftengine.core.plugin.context.ContextHolder;
import net.momirealms.craftengine.core.plugin.context.ContextKey;
import net.momirealms.craftengine.core.plugin.context.parameter.DirectContextParameters;
import net.momirealms.craftengine.core.util.Key;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class CraftEngineProvider implements Provider<ItemStack, Player> {
    public static final CraftEngineProvider INSTANCE = new CraftEngineProvider();

    private CraftEngineProvider() {}

    @Override
    public String plugin() {
        return "craftengine";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        Key itemId = Key.of(id);
        CustomItem<ItemStack> customItem = CraftEngineItems.byId(itemId);
        if (customItem == null) {
            return Optional.empty();
        }
        ContextHolder.Builder builder = ContextHolder.builder().withParameter(DirectContextParameters.ID, itemId);
        ItemBuildContext itemBuildContext = ItemBuildContext.of(player == null ? null : BukkitAdaptors.adapt(player), adapt(builder, context));
        return Optional.ofNullable(customItem.buildItemStack(itemBuildContext));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        Key itemId = Key.of(id);
        CustomItem<ItemStack> customItem = CraftEngineItems.byId(itemId);
        if (customItem == null) {
            return null;
        }
        ContextHolder.Builder builder = ContextHolder.builder().withParameter(DirectContextParameters.ID, itemId);
        ItemBuildContext itemBuildContext = ItemBuildContext.of(player == null ? null : BukkitAdaptors.adapt(player), adapt(builder, context));
        return customItem.buildItemStack(itemBuildContext);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(CraftEngineItems.getCustomItemId(item)).map(Key::asString);
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        Key itemId = CraftEngineItems.getCustomItemId(item);
        if (itemId == null) {
            return null;
        }
        return itemId.asString();
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return CraftEngineItems.isCustomItem(item);
    }

    @Override
    public boolean has(@NotNull String id) {
        return CraftEngineItems.loadedItems().containsKey(Key.of(id));
    }

    private static ContextHolder.Builder adapt(ContextHolder.Builder builder, BuildContext context) {
        for (Map.Entry<cn.gtemc.itembridge.api.context.ContextKey<?>, Supplier<Object>> entry : context.contextData().entrySet()) {
            withParameter(builder, ContextKey.direct(entry.getKey().key()), entry.getValue());
        }
        return builder;
    }

    @SuppressWarnings("unchecked")
    private static <T, C> void withParameter(ContextHolder.Builder builder, ContextKey<T> key, Supplier<C> value) {
        builder.withParameter(key, (T) value);
    }
}
