package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SlimefunProvider implements Provider<ItemStack> {
    public static final SlimefunProvider INSTANCE = new SlimefunProvider();

    private SlimefunProvider() {}

    @Override
    public String plugin() {
        return "slimefun";
    }

    @Override
    public Optional<ItemStack> build(String id, @NotNull BuildContext context) {
        SlimefunItem item = SlimefunItem.getById(id);
        if (item == null) {
            return Optional.empty();
        }
        return Optional.of(item.getItem());
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if (slimefunItem == null) {
            return Optional.empty();
        }
        return Optional.of(slimefunItem.getId());
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return SlimefunItem.getByItem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return SlimefunItem.getById(id) != null;
    }
}
