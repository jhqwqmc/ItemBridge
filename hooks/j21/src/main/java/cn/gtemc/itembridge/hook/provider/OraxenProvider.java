package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class OraxenProvider implements Provider<ItemStack, Player> {
    public static final OraxenProvider INSTANCE = new OraxenProvider();

    private OraxenProvider() {}

    @Override
    public String plugin() {
        return "oraxen";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        ItemBuilder item = OraxenItems.getItemById(id);
        if (item == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(item.build());
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        ItemBuilder item = OraxenItems.getItemById(id);
        if (item == null) {
            return null;
        }
        return item.build();
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(OraxenItems.getIdByItem(item));
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return OraxenItems.getIdByItem(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return OraxenItems.exists(item);
    }

    @Override
    public boolean has(@NotNull String id) {
        return OraxenItems.exists(id);
    }
}
