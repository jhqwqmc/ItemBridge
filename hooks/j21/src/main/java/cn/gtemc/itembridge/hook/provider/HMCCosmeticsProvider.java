package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import com.hibiscusmc.hmccosmetics.api.HMCCosmeticsAPI;
import com.hibiscusmc.hmccosmetics.cosmetic.Cosmetic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class HMCCosmeticsProvider implements Provider<ItemStack, Player> {
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
    public boolean is(@NotNull ItemStack item) {
        return id(item).isPresent();
    }

    @Override
    public boolean has(@NotNull String id) {
        return HMCCosmeticsAPI.getCosmetic(id) != null;
    }
}
