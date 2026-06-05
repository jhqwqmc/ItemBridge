package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.context.ContextKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tabooproject.baikiruto.core.Baikiruto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

final class BaikirutoProvider implements Provider<ItemStack, Player> {
    public static final BaikirutoProvider INSTANCE = new BaikirutoProvider();

    private BaikirutoProvider() {}

    @Override
    public String plugin() {
        return "baikiruto";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Optional.ofNullable(Baikiruto.INSTANCE.api().buildItem(id, adapt(player, context)));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Baikiruto.INSTANCE.api().buildItem(id, adapt(player, context));
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(Baikiruto.INSTANCE.api().getItemId(item));
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return Baikiruto.INSTANCE.api().getItemId(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return Baikiruto.INSTANCE.api().getItemId(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return Baikiruto.INSTANCE.api().getItem(id) != null;
    }

    private static Map<String, Object> adapt(@Nullable Player player, @NotNull BuildContext context) {
        Map<ContextKey<?>, Supplier<Object>> contextData = context.contextData();
        if (contextData.isEmpty()) {
            if (player == null) {
                return Collections.emptyMap();
            } else {
                return Collections.singletonMap("player", player);
            }
        }
        Map<String, Object> params = new HashMap<>();
        if (player != null) {
            params.put("player", player);
        }
        for (Map.Entry<ContextKey<?>, Supplier<Object>> entry : contextData.entrySet()) {
            params.put(entry.getKey().key(), entry.getValue().get());
        }
        return params;
    }

    static boolean conflictCheck(Plugin plugin) {
        return Utils.classExists("org{}tabooproject{}baikiruto{}core{}Baikiruto");
    }
}
