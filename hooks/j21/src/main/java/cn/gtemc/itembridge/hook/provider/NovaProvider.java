package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.nova.api.Nova;
import xyz.xenondevs.nova.api.item.NovaItem;

import java.util.Optional;

public class NovaProvider implements Provider<ItemStack, Player> {
    public static final NovaProvider INSTANCE = new NovaProvider();

    private NovaProvider() {}

    @Override
    public String plugin() {
        return "nova";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        NovaItem novaItem = Nova.getNova().getItemRegistry().getOrNull(id);
        if (novaItem == null) {
            return Optional.empty();
        }
        return Optional.of(novaItem.createItemStack());
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        NovaItem novaItem = Nova.getNova().getItemRegistry().getOrNull(item);
        if (novaItem == null) {
            return Optional.empty();
        }
        return Optional.of(novaItem.getId().toString());
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return Nova.getNova().getItemRegistry().getOrNull(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return Nova.getNova().getItemRegistry().getOrNull(id) != null;
    }
}
