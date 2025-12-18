package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class NexoProvider implements Provider<ItemStack, Player> {
    public static final NexoProvider INSTANCE = new NexoProvider();

    private NexoProvider() {}

    @Override
    public String plugin() {
        return "nexo";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        ItemBuilder itemBuilder = NexoItems.itemFromId(id);
        if (itemBuilder == null) {
            return Optional.empty();
        }
        return Optional.of(itemBuilder.build());
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        ItemBuilder itemBuilder = NexoItems.itemFromId(id);
        if (itemBuilder == null) {
            return null;
        }
        return itemBuilder.build();
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(NexoItems.idFromItem(item));
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return NexoItems.idFromItem(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return NexoItems.exists(item);
    }

    @Override
    public boolean has(@NotNull String id) {
        return NexoItems.exists(id);
    }
}
