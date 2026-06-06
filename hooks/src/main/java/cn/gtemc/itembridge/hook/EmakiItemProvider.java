package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import emaki.jiuwu.craft.item.api.EmakiItemApi;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

final class EmakiItemProvider implements Provider<ItemStack, Player> {
    public static final EmakiItemProvider INSTANCE = new EmakiItemProvider();

    private EmakiItemProvider() {}

    @Override
    public String plugin() {
        return "emakiitem";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Optional.ofNullable(EmakiItemApi.create(id, 1));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        return EmakiItemApi.create(id, 1);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(EmakiItemApi.identify(item));
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return EmakiItemApi.identify(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return EmakiItemApi.identify(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return EmakiItemApi.exists(id);
    }

    final static class Check {
        static boolean conflictCheck(Plugin plugin) {
            return Utils.classExists("emaki{}jiuwu{}craft{}item{}api{}EmakiItemApi");
        }
    }
}
