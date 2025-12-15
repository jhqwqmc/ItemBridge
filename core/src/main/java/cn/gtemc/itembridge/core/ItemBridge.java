package cn.gtemc.itembridge.core;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Optional;

public interface ItemBridge {

    static Builder builder() {
        return new ItemBridgeImpl.BuilderImpl();
    }

    Collection<Provider<ItemStack>> providers();

    Optional<ItemStack> build(String plugin, String id, BuildContext context);

    boolean hasPlugin(String plugin);

    interface Builder {

        Builder register(Provider<ItemStack> provider);

        Provider<ItemStack> removeById(String id);

        ItemBridge build();
    }
}
