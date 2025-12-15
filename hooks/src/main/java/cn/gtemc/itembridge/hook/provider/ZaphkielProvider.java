package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.hook.context.ItemContextKeys;
import ink.ptms.zaphkiel.Zaphkiel;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ZaphkielProvider implements Provider<ItemStack> {
    public static final ZaphkielProvider INSTANCE = new ZaphkielProvider();

    private ZaphkielProvider() {}

    @Override
    public String plugin() {
        return "zaphkiel";
    }

    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
        Player player = context.getOrNull(ItemContextKeys.PLAYER);
        return Optional.ofNullable(Zaphkiel.INSTANCE.api().getItemManager().generateItemStack(id, player));
    }

    @Override
    public Optional<String> id(ItemStack item) {
        return Optional.ofNullable(Zaphkiel.INSTANCE.api().getItemHandler().getItemId(item));
    }

    @Override
    public boolean is(ItemStack item) {
        return Zaphkiel.INSTANCE.api().getItemHandler().getItem(item) != null;
    }

    @Override
    public boolean has(String id) {
        return Zaphkiel.INSTANCE.api().getItemManager().getItem(id) != null;
    }
}
