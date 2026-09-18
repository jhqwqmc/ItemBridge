package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.context.ContextKey;
import com.dre.brewery.api.BreweryApi;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

final class BreweryXProvider implements Provider<ItemStack, Player> {
    public static final BreweryXProvider INSTANCE = new BreweryXProvider();
    private static final ContextKey<Integer> QUALITY = ContextKey.of(Integer.class, "breweryx.quality");

    private BreweryXProvider() {}

    @Override
    public String plugin() {
        return "breweryx";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Optional.ofNullable(BreweryApi.createBrewItem(id, context.getOrDefault(QUALITY, 10), player));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        return BreweryApi.createBrewItem(id, context.getOrDefault(QUALITY, 10), player);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(BreweryApi.getRecipeName(item));
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return BreweryApi.getRecipeName(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return BreweryApi.isBrew(item);
    }

    @Override
    public boolean has(@NotNull String id) {
        return BreweryApi.getRecipe(id) != null;
    }

    final static class Check {
        static boolean conflictCheck(Plugin plugin) {
            return Utils.classExists("com{}dre{}brewery{}api{}BreweryApi");
        }
    }
}
