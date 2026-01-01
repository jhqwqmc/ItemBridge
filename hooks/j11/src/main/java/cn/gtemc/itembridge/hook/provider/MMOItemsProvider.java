package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MMOItemsProvider implements Provider<ItemStack, Player> {
    public static final MMOItemsProvider INSTANCE = new MMOItemsProvider();

    private MMOItemsProvider() {}

    @Override
    public String plugin() {
        return "mmoitems";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        String[] split = id.split(":", 2);
        if (split.length == 1) {
            split = split[0].split("_", 2);
        }
        if (split.length == 1) {
            return Optional.empty();
        }
        String mmoItemId = split[1].toUpperCase().replace("-", "_").replace(" ", "_");
        MMOItem mmoItem = MMOItems.plugin.getMMOItem(Type.get(split[0]), mmoItemId);
        return Optional.ofNullable(mmoItem).map(it -> it.newBuilder().build());
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        String[] split = id.split(":", 2);
        if (split.length == 1) {
            split = split[0].split("_", 2);
        }
        if (split.length == 1) {
            return null;
        }
        String mmoItemId = split[1].toUpperCase().replace("-", "_").replace(" ", "_");
        MMOItem mmoItem = MMOItems.plugin.getMMOItem(Type.get(split[0]), mmoItemId);
        if (mmoItem == null) {
            return null;
        }
        return mmoItem.newBuilder().build();
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        Type type = MMOItems.getType(item);
        String id = MMOItems.getID(item);
        if (type == null || id == null) {
            return Optional.empty();
        }
        return Optional.of(type + "_" + id);
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        Type type = MMOItems.getType(item);
        String id = MMOItems.getID(item);
        if (type == null || id == null) {
            return null;
        }
        return type + "_" + id;
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        Type type = MMOItems.getType(item);
        String id = MMOItems.getID(item);
        return type != null && id != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        String[] split = id.split(":", 2);
        if (split.length == 1) {
            split = split[0].split("_", 2);
        }
        if (split.length == 1) {
            return false;
        }
        String mmoItemId = split[1].toUpperCase().replace("-", "_").replace(" ", "_");
        return MMOItems.plugin.getMMOItem(Type.get(split[0]), mmoItemId) != null;
    }
}
