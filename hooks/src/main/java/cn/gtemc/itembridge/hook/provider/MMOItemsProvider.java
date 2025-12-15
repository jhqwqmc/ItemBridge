package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class MMOItemsProvider implements Provider<ItemStack> {
    public static final MMOItemsProvider INSTANCE = new MMOItemsProvider();

    private MMOItemsProvider() {}

    @Override
    public String plugin() {
        return "mmoitems";
    }

    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
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
    public Optional<String> id(ItemStack item) {
        Type type = MMOItems.getType(item);
        String id = MMOItems.getID(item);
        if (type == null || id == null) {
            return Optional.empty();
        }
        return Optional.of(type + "_" + id);
    }

    @Override
    public boolean is(ItemStack item) {
        Type type = MMOItems.getType(item);
        String id = MMOItems.getID(item);
        return type != null && id != null;
    }

    @Override
    public boolean has(String id) {
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
