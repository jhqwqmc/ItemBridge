package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import me.wolfyscript.customcrafting.CustomCrafting;
import me.wolfyscript.utilities.api.inventory.custom_items.CustomItem;
import me.wolfyscript.utilities.util.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

final class CustomCraftingProvider implements Provider<ItemStack, Player> {
    public static final CustomCraftingProvider INSTANCE = new CustomCraftingProvider();

    private CustomCraftingProvider() {}

    @Override
    public String plugin() {
        return "customcrafting";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        CustomItem customItem = CustomCrafting.inst().getApi().getRegistries().getCustomItems().get(NamespacedKey.of(id));
        if (customItem == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(customItem.create(1));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        CustomItem customItem = CustomCrafting.inst().getApi().getRegistries().getCustomItems().get(NamespacedKey.of(id));
        if (customItem == null) {
            return null;
        }
        return customItem.create(1);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return CustomCrafting.inst().getApi().getRegistries().getCustomItems().getByItemStack(item)
                .map(CustomItem::getNamespacedKey)
                .map(NamespacedKey::toString);
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        CustomItem customItem = CustomCrafting.inst().getApi().getRegistries().getCustomItems().getByItemStack(item).orElse(null);
        if (customItem == null) {
            return null;
        }
        NamespacedKey key = customItem.getNamespacedKey();
        if (key == null) {
            return null;
        }
        return key.toString();
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return CustomCrafting.inst().getApi().getRegistries().getCustomItems().getByItemStack(item).isPresent();
    }

    @Override
    public boolean has(@NotNull String id) {
        return CustomCrafting.inst().getApi().getRegistries().getCustomItems().has(NamespacedKey.of(id));
    }

    final static class Check {
        static boolean conflictCheck(Plugin plugin) {
            return Utils.classExists("me{}wolfyscript{}customcrafting{}CustomCrafting");
        }
    }
}
