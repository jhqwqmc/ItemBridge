package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.util.MiscUtils;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.sobject.SObjectInterface;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExecutableItemsProvider implements Provider<ItemStack, Player> {
    public static final ExecutableItemsProvider INSTANCE = new ExecutableItemsProvider();

    private ExecutableItemsProvider() {}

    @Override
    public String plugin() {
        return "executableitems";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return ExecutableItemsAPI.getExecutableItemsManager()
                .getExecutableItem(id)
                .map(item -> {
                    Map<String, Object> map = MiscUtils.adaptString2Object(context);
                    return item.buildItem(1, Optional.ofNullable(player), map != null ? map : new HashMap<>());
                });
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        return build(id, player, context).orElse(null);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return ExecutableItemsAPI.getExecutableItemsManager()
                .getExecutableItem(item)
                .map(SObjectInterface::getId);
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return id(item).orElse(null);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(item).isPresent();
    }

    @Override
    public boolean has(@NotNull String id) {
        return ExecutableItemsAPI.getExecutableItemsManager().isValidID(id);
    }
}
