package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import com.willfp.eco.core.items.CustomItem;
import com.willfp.eco.core.items.Items;
import com.willfp.eco.core.items.TestableItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record EcoProvider(@Override String plugin) implements Provider<ItemStack> {

    @Override
    public Optional<ItemStack> build(String id, @NotNull BuildContext context) {
        TestableItem item = Items.lookup(plugin + ":" + id);
        if (!(item instanceof CustomItem customItem)) {
            return Optional.empty();
        }
        return Optional.ofNullable(customItem.getItem());
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        CustomItem customItem = Items.getCustomItem(item);
        if (customItem == null) {
            return Optional.empty();
        }
        return Optional.of(customItem.getKey().toString());
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return Items.getCustomItem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return Items.lookup(plugin + ":" + id) instanceof CustomItem;
    }
}
