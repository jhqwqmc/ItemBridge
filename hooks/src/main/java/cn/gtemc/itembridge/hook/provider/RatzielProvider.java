package cn.gtemc.itembridge.hook.provider;

import cn.fd.ratziel.core.contextual.SimpleContext;
import cn.fd.ratziel.module.item.ItemManager;
import cn.fd.ratziel.module.item.api.builder.ItemGenerator;
import cn.fd.ratziel.module.item.impl.RatzielItem;
import cn.fd.ratziel.module.item.util.NeoItemUtilKt;
import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public class RatzielProvider implements Provider<ItemStack, Player> {
    public static final RatzielProvider INSTANCE = new RatzielProvider();

    private RatzielProvider() {}

    @Override
    public String plugin() {
        return "ratziel";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        ItemGenerator itemGenerator = ItemManager.INSTANCE.getRegistry().get(id);
        if (itemGenerator == null) {
            return Optional.empty();
        }
        if (player == null) {
            return Optional.ofNullable(itemGenerator.build().thenApply(NeoItemUtilKt::toItemStack).join());
        }
        SimpleContext simpleContext = new SimpleContext(player);
        for (Supplier<Object> value : context.contextData().values()) {
            simpleContext.put(value.get());
        }
        return Optional.ofNullable(itemGenerator.build(simpleContext).thenApply(NeoItemUtilKt::toItemStack).join());
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        ItemGenerator itemGenerator = ItemManager.INSTANCE.getRegistry().get(id);
        if (itemGenerator == null) {
            return null;
        }
        if (player == null) {
            return itemGenerator.build().thenApply(NeoItemUtilKt::toItemStack).join();
        }
        SimpleContext simpleContext = new SimpleContext(player);
        for (Supplier<Object> value : context.contextData().values()) {
            simpleContext.put(value.get());
        }
        return itemGenerator.build(simpleContext).thenApply(NeoItemUtilKt::toItemStack).join();
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        RatzielItem ratzielItem = RatzielItem.of(item);
        if (ratzielItem == null) {
            return Optional.empty();
        }
        return Optional.of(ratzielItem.getIdentifier().getContent());
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        RatzielItem ratzielItem = RatzielItem.of(item);
        if (ratzielItem == null) {
            return null;
        }
        return ratzielItem.getIdentifier().getContent();
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return RatzielItem.isRatzielItem(item);
    }

    @Override
    public boolean has(@NotNull String id) {
        return ItemManager.INSTANCE.getRegistry().containsKey(id);
    }
}
