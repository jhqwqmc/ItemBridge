package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import ink.ptms.zaphkiel.Zaphkiel;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ZaphkielProvider implements Provider<ItemStack, Player> {
    public static final ZaphkielProvider INSTANCE = new ZaphkielProvider();

    private ZaphkielProvider() {}

    @Override
    public String plugin() {
        return "zaphkiel";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Optional.ofNullable(Zaphkiel.INSTANCE.api().getItemManager().generateItemStack(id, player));
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(Zaphkiel.INSTANCE.api().getItemHandler().getItemId(item));
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return Zaphkiel.INSTANCE.api().getItemHandler().getItem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return Zaphkiel.INSTANCE.api().getItemManager().getItem(id) != null;
    }
}
