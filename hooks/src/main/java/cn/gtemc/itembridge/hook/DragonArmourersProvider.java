package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import eos.moe.armourers.api.DragonAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

final class DragonArmourersProvider implements Provider<ItemStack, Player> {
    public static final DragonArmourersProvider INSTANCE = new DragonArmourersProvider();

    private DragonArmourersProvider() {}

    @Override
    public String plugin() {
        return "dragonarmourers";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Optional.ofNullable(DragonAPI.setItemSkin(new ItemStack(Material.MAGMA_CREAM), id));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        return DragonAPI.setItemSkin(new ItemStack(Material.MAGMA_CREAM), id);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(DragonAPI.getItemSkinName(item));
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return DragonAPI.getItemSkinName(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return DragonAPI.getItemSkinName(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return DragonAPI.getAllSkins().contains(id);
    }

    final static class Check {
        static boolean conflictCheck(Plugin plugin) {
            return Utils.classExists("eos{}moe{}armourers{}api{}DragonAPI");
        }
    }
}
