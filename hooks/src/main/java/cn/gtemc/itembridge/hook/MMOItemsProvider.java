package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

final class MMOItemsProvider implements Provider<ItemStack, Player> {
    public static final MMOItemsProvider INSTANCE = new MMOItemsProvider();

    private MMOItemsProvider() {}

    @Override
    public String plugin() {
        return "mmoitems";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        String[] split = Utils.decompose(id, ':');
        if (split == null) {
            split = Utils.decompose(id, '_');
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
        String[] split = Utils.decompose(id, ':');
        if (split == null) {
            split = Utils.decompose(id, '_');
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
        return Optional.of(type.getId() + "_" + id);
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        Type type = MMOItems.getType(item);
        String id = MMOItems.getID(item);
        if (type == null || id == null) {
            return null;
        }
        return type.getId() + "_" + id;
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        Type type = MMOItems.getType(item);
        String id = MMOItems.getID(item);
        return type != null && id != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        String[] split = Utils.decompose(id, ':');
        if (split == null) {
            split = Utils.decompose(id, '_');
        }
        if (split.length == 1) {
            return false;
        }
        String mmoItemId = split[1].toUpperCase().replace("-", "_").replace(" ", "_");
        return MMOItems.plugin.getMMOItem(Type.get(split[0]), mmoItemId) != null;
    }

    final static class Check {
        static boolean conflictCheck(Plugin plugin) {
            return Utils.classExists("net{}Indyuce{}mmoitems{}MMOItems");
        }
    }
}
