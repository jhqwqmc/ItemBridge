package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import com.ssomar.score.api.executableblocks.ExecutableBlocksAPI;
import com.ssomar.score.sobject.SObjectInterface;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

final class ExecutableBlocksProvider implements Provider<ItemStack, Player> {
    public static final ExecutableBlocksProvider INSTANCE = new ExecutableBlocksProvider();

    private ExecutableBlocksProvider() {}

    @Override
    public String plugin() {
        return "executableblocks";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return ExecutableBlocksAPI.getExecutableBlocksManager()
                .getExecutableBlock(id)
                .map(item -> {
                    Map<String, Object> map = Utils.adaptString2Object(context);
                    return item.buildItem(1, Optional.ofNullable(player), map != null ? map : new HashMap<>());
                });
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        return build(id, player, context).orElse(null);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return ExecutableBlocksAPI.getExecutableBlocksManager()
                .getExecutableBlock(item)
                .map(SObjectInterface::getId);
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return id(item).orElse(null);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return ExecutableBlocksAPI.getExecutableBlocksManager().getExecutableBlock(item).isPresent();
    }

    @Override
    public boolean has(@NotNull String id) {
        return ExecutableBlocksAPI.getExecutableBlocksManager().isValidID(id);
    }

    static boolean conflictCheck(Plugin plugin) {
        return Utils.classExists("com{}ssomar{}score{}api{}executableblocks{}ExecutableBlocksAPI");
    }
}
