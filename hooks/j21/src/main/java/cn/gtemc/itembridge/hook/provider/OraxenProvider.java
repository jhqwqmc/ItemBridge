package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class OraxenProvider implements Provider<ItemStack> {
    public static final OraxenProvider INSTANCE = new OraxenProvider();

    private OraxenProvider() {}

    @Override
    public String plugin() {
        return "oraxen";
    }

    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
        ItemBuilder item = OraxenItems.getItemById(id);
        if (item == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(item.build());
    }

    @Override
    public Optional<String> id(ItemStack item) {
        return Optional.ofNullable(OraxenItems.getIdByItem(item));
    }

    @Override
    public boolean is(ItemStack item) {
        return OraxenItems.exists(item);
    }

    @Override
    public boolean has(String id) {
        return OraxenItems.exists(id);
    }
}
