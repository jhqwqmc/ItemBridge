package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.util.MiscUtils;
import io.github.zzzyyylllty.sertraline.Sertraline;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SertralineProvider implements Provider<ItemStack, Player> {
    public static final SertralineProvider INSTANCE = new SertralineProvider();

    private SertralineProvider() {}

    @Override
    public String plugin() {
        return "sertraline";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Optional.ofNullable(Sertraline.INSTANCE.api().buildItem(id, player, null, 1, MiscUtils.adaptString2Object(context)));
    }

    @Nullable
    @Override
    public ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Sertraline.INSTANCE.api().buildItem(id, player, null, 1, MiscUtils.adaptString2Object(context));
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(Sertraline.INSTANCE.api().getId(item));
    }

    @Nullable
    @Override
    public String idOrNull(@NotNull ItemStack item) {
        return Sertraline.INSTANCE.api().getId(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return Sertraline.INSTANCE.api().isVaildItem(item);
    }

    @Override
    public boolean has(@NotNull String id) {
        return Sertraline.INSTANCE.api().isRegisteredItem(id);
    }
}
