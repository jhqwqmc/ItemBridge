package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.hook.context.ItemContextKeys;
import net.momirealms.craftengine.bukkit.api.BukkitAdaptors;
import net.momirealms.craftengine.bukkit.api.CraftEngineItems;
import net.momirealms.craftengine.core.item.CustomItem;
import net.momirealms.craftengine.core.util.Key;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class CraftEngineProvider implements Provider<ItemStack> {
    public static final CraftEngineProvider INSTANCE = new CraftEngineProvider();

    private CraftEngineProvider() {}

    @Override
    public String plugin() {
        return "craftengine";
    }

    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
        CustomItem<ItemStack> customItem = CraftEngineItems.byId(Key.of(id));
        if (customItem == null) {
            return Optional.empty();
        }
        return context.get(ItemContextKeys.PLAYER)
                .map(BukkitAdaptors::adapt)
                .map(customItem::buildItemStack)
                .or(() -> Optional.of(customItem.buildItemStack()));
    }

    @Override
    public Optional<String> id(ItemStack item) {
        return Optional.ofNullable(CraftEngineItems.getCustomItemId(item)).map(Key::asString);
    }

    @Override
    public boolean is(ItemStack item) {
        return CraftEngineItems.isCustomItem(item);
    }

    @Override
    public boolean has(String id) {
        return CraftEngineItems.loadedItems().containsKey(Key.of(id));
    }
}
