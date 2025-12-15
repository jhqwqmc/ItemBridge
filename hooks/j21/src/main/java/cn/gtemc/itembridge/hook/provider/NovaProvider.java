package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.nova.api.Nova;
import xyz.xenondevs.nova.api.item.NovaItem;

import java.util.Optional;

public class NovaProvider implements Provider<ItemStack> {
    public static final NovaProvider INSTANCE = new NovaProvider();

    private NovaProvider() {}

    @Override
    public String plugin() {
        return "nova";
    }

    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
        NovaItem novaItem = Nova.getNova().getItemRegistry().getOrNull(id);
        if (novaItem == null) {
            return Optional.empty();
        }
        return Optional.of(novaItem.createItemStack());
    }

    @Override
    public Optional<String> id(ItemStack item) {
        NovaItem novaItem = Nova.getNova().getItemRegistry().getOrNull(item);
        if (novaItem == null) {
            return Optional.empty();
        }
        return Optional.of(novaItem.getId().toString());
    }

    @Override
    public boolean is(ItemStack item) {
        return Nova.getNova().getItemRegistry().getOrNull(item) != null;
    }

    @Override
    public boolean has(String id) {
        return Nova.getNova().getItemRegistry().getOrNull(id) != null;
    }
}
