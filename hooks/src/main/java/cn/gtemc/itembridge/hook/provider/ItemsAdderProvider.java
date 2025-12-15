package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemsAdderProvider implements Provider<ItemStack> {
    public static final ItemsAdderProvider INSTANCE = new ItemsAdderProvider();

    private ItemsAdderProvider() {}

    @Override
    public String plugin() {
        return "itemsadder";
    }

    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
        CustomStack instance = CustomStack.getInstance(id);
        if (instance == null) {
            return Optional.empty();
        }
        return Optional.of(instance.getItemStack());
    }

    @Override
    public Optional<String> id(ItemStack item) {
        CustomStack customStack = CustomStack.byItemStack(item);
        if (customStack == null) {
            return Optional.empty();
        }
        return Optional.of(customStack.getNamespacedID());
    }

    @Override
    public boolean is(ItemStack item) {
        return CustomStack.byItemStack(item) != null;
    }

    @Override
    public boolean has(String id) {
        return CustomStack.isInRegistry(id);
    }
}
