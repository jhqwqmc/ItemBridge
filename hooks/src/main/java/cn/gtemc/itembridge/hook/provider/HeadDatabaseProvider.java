package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HeadDatabaseProvider implements Provider<ItemStack, Player> {
    public static final HeadDatabaseProvider INSTANCE = new HeadDatabaseProvider();
    private final HeadDatabaseAPI api = new HeadDatabaseAPI();

    private HeadDatabaseProvider() {}

    @Override
    public String plugin() {
        return "headdatabase";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Optional.ofNullable(api.getItemHead(id));
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(api.getItemID(item));
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return api.getItemID(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return api.isHead(id);
    }
}
