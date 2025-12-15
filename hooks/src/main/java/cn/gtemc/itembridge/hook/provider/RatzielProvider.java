package cn.gtemc.itembridge.hook.provider;

import cn.fd.ratziel.core.contextual.SimpleContext;
import cn.fd.ratziel.module.item.ItemManager;
import cn.fd.ratziel.module.item.api.builder.ItemGenerator;
import cn.fd.ratziel.module.item.impl.RatzielItem;
import cn.fd.ratziel.module.item.util.NeoItemUtilKt;
import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.hook.context.ItemContextKeys;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class RatzielProvider implements Provider<ItemStack> {
    public static final RatzielProvider INSTANCE = new RatzielProvider();

    private RatzielProvider() {}

    @Override
    public String plugin() {
        return "ratziel";
    }

    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
        ItemGenerator itemGenerator = ItemManager.INSTANCE.getRegistry().get(id);
        if (itemGenerator == null) {
            return Optional.empty();
        }
        return context.get(ItemContextKeys.PLAYER)
                .map(SimpleContext::new)
                .map(ctx -> itemGenerator.build(ctx).thenApply(NeoItemUtilKt::toItemStack).join())
                .or(() -> Optional.ofNullable(itemGenerator.build().thenApply(NeoItemUtilKt::toItemStack).join()));
    }

    @Override
    public Optional<String> id(ItemStack item) {
        RatzielItem ratzielItem = RatzielItem.of(item);
        if (ratzielItem == null) {
            return Optional.empty();
        }
        return Optional.of(ratzielItem.getIdentifier().getContent());
    }

    @Override
    public boolean is(ItemStack item) {
        return RatzielItem.isRatzielItem(item);
    }

    @Override
    public boolean has(String id) {
        return ItemManager.INSTANCE.getRegistry().containsKey(id);
    }
}
