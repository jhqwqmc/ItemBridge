package cn.gtemc.itembridge.hook.context;

import cn.gtemc.itembridge.api.context.ContextKey;
import org.bukkit.entity.Player;

public final class ItemContextKeys {

    private ItemContextKeys() {}

    public static final ContextKey<Player> PLAYER = ContextKey.of(Player.class, "player");
}
