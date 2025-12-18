package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pku.yim.magicgem.gem.Gem;
import pku.yim.magicgem.gem.GemManager;

import java.util.Optional;

public class MagicGemProvider implements Provider<ItemStack, Player> {
    public static final MagicGemProvider INSTANCE = new MagicGemProvider();

    private MagicGemProvider() {}

    @Override
    public String plugin() {
        return "magicgem";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        Gem gem = GemManager.getGemByName(id);
        if (gem == null) {
            return Optional.empty();
        }
        return Optional.of(gem.getRealGem());
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        Gem gem = GemManager.getGemByName(id);
        if (gem == null) {
            return null;
        }
        return gem.getRealGem();
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        Gem gem = GemManager.getGem(item);
        if (gem == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(gem.getName());
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        Gem gem = GemManager.getGem(item);
        if (gem == null) {
            return null;
        }
        return gem.getName();
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return GemManager.getGem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return GemManager.getGemByName(id) != null;
    }
}
