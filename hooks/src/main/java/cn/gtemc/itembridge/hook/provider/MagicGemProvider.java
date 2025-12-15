package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import org.bukkit.inventory.ItemStack;
import pku.yim.magicgem.gem.Gem;
import pku.yim.magicgem.gem.GemManager;

import java.util.Optional;

public class MagicGemProvider implements Provider<ItemStack> {
    public static final MagicGemProvider INSTANCE = new MagicGemProvider();

    private MagicGemProvider() {}

    @Override
    public String plugin() {
        return "magicgem";
    }

    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
        Gem gem = GemManager.getGemByName(id);
        if (gem == null) {
            return Optional.empty();
        }
        return Optional.of(gem.getRealGem());
    }

    @Override
    public Optional<String> id(ItemStack item) {
        Gem gem = GemManager.getGem(item);
        if (gem == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(gem.getName());
    }

    @Override
    public boolean is(ItemStack item) {
        return GemManager.getGem(item) != null;
    }

    @Override
    public boolean has(String id) {
        return GemManager.getGemByName(id) != null;
    }
}
