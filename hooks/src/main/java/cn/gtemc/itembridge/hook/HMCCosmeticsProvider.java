package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import com.hibiscusmc.hmccosmetics.api.HMCCosmeticsAPI;
import com.hibiscusmc.hmccosmetics.cosmetic.Cosmetic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

final class HMCCosmeticsProvider implements Provider<ItemStack, Player> {
    public static final HMCCosmeticsProvider INSTANCE = new HMCCosmeticsProvider();

    private HMCCosmeticsProvider() {}

    @Override
    public String plugin() {
        return "hmccosmetics";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        Cosmetic cosmetic = HMCCosmeticsAPI.getCosmetic(id);
        if (cosmetic == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(cosmetic.getItem());
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        Cosmetic cosmetic = HMCCosmeticsAPI.getCosmetic(id);
        if (cosmetic == null) {
            return null;
        }
        return cosmetic.getItem();
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        List<Cosmetic> allCosmetics = HMCCosmeticsAPI.getAllCosmetics();
        for (Cosmetic cosmetic : allCosmetics) {
            ItemStack itemStack = cosmetic.getItem();
            if (itemStack != null && itemStack.equals(item)) {
                return Optional.of(cosmetic.getId());
            }
        }
        return Optional.empty();
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        List<Cosmetic> allCosmetics = HMCCosmeticsAPI.getAllCosmetics();
        for (Cosmetic cosmetic : allCosmetics) {
            ItemStack itemStack = cosmetic.getItem();
            if (itemStack != null && itemStack.equals(item)) {
                return cosmetic.getId();
            }
        }
        return null;
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return idOrNull(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return HMCCosmeticsAPI.getCosmetic(id) != null;
    }

    static boolean conflictCheck(Plugin plugin) {
        return Utils.classExists("com{}hibiscusmc{}hmccosmetics{}api{}HMCCosmeticsAPI");
    }
}
