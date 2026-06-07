package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import com.willfp.eco.core.items.CustomItem;
import com.willfp.eco.core.items.Items;
import com.willfp.eco.core.items.TestableItem;
import com.willfp.eco.core.recipe.parts.GroupedTestableItems;
import com.willfp.eco.core.recipe.parts.ModifiedTestableItem;
import com.willfp.eco.core.recipe.parts.TestableStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Optional;

final class EcoProvider implements Provider<ItemStack, Player> {
    private final String plugin;

    public EcoProvider(@NotNull String plugin) {
        this.plugin = plugin;
    }

    @Override
    public String plugin() {
        return this.plugin;
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        CustomItem item = getCustomItem(Items.lookup(plugin + ":" + id));
        if (item == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(item.getItem());
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        CustomItem item = getCustomItem(Items.lookup(plugin + ":" + id));
        if (item == null) {
            return null;
        }
        return item.getItem();
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        CustomItem customItem = Items.getCustomItem(item);
        if (customItem == null) {
            return Optional.empty();
        }
        return Optional.of(customItem.getKey().toString());
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        CustomItem customItem = Items.getCustomItem(item);
        if (customItem == null) {
            return null;
        }
        return customItem.getKey().toString();
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return Items.getCustomItem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return Items.lookup(plugin + ":" + id) instanceof CustomItem;
    }

    private static @Nullable CustomItem getCustomItem(TestableItem item) {
        if (item instanceof CustomItem) {
            return (CustomItem) item;
        }
        if (item instanceof ModifiedTestableItem) {
            return getCustomItem(((ModifiedTestableItem) item).getHandle());
        }
        if (item instanceof TestableStack) {
            return getCustomItem(((TestableStack) item).getHandle());
        }
        if (item instanceof GroupedTestableItems) {
            Iterator<TestableItem> iterator = ((GroupedTestableItems) item).getChildren().iterator();
            if (iterator.hasNext()) {
                return getCustomItem(iterator.next());
            }
        }
        return null;
    }

    final static class Check {
        static boolean conflictCheck(Plugin plugin) {
            return Utils.classExists("com{}willfp{}eco{}core{}items{}Items");
        }
    }
}
