package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import com.pxpmc.pxrpg.api.MAPI;
import com.pxpmc.pxrpg.api.Module;
import com.pxpmc.pxrpg.api.adapter.AdapterItemStack;
import com.pxpmc.pxrpg.api.adapter.AdapterPlayer;
import com.pxpmc.pxrpg.api.modules.item.ItemConfig;
import com.pxpmc.pxrpg.api.modules.item.ItemInter;
import com.pxpmc.pxrpg.api.modules.item.ItemManager;
import com.pxpmc.pxrpg.api.modules.item.ItemModule;
import com.pxpmc.pxrpg.api.util.ParameterResolver;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PxRpgProvider implements Provider<ItemStack, Player> {
    public static final PxRpgProvider INSTANCE = new PxRpgProvider();
    private final ItemManager itemManager = Module.getModule(ItemModule.class).getItemManager();

    private PxRpgProvider() {}

    @Override
    public String plugin() {
        return "pxrpg";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        String[] split = id.split(",", 2);
        String itemId = split[0];
        ItemConfig itemConfig = itemManager.getRegister(itemId);
        if (itemConfig == null) {
            return Optional.empty();
        }
        AdapterPlayer pxRpgPlayer = MAPI.getBukkitPxRpgAPI().toPxRpgPlayer(player);
        ParameterResolver resolver = new ParameterResolver();
        if (split.length == 2) {
            resolver.parser(split[1]);
        }
        AdapterItemStack adapterItemStack = itemManager.spawnItemStack(itemConfig, pxRpgPlayer, null, resolver);
        if (adapterItemStack == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(MAPI.getBukkitPxRpgAPI().toBukkitItemStack(adapterItemStack));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        String[] split = id.split(",", 2);
        String itemId = split[0];
        ItemConfig itemConfig = itemManager.getRegister(itemId);
        if (itemConfig == null) {
            return null;
        }
        AdapterPlayer pxRpgPlayer = MAPI.getBukkitPxRpgAPI().toPxRpgPlayer(player);
        ParameterResolver resolver = new ParameterResolver();
        if (split.length == 2) {
            resolver.parser(split[1]);
        }
        AdapterItemStack adapterItemStack = itemManager.spawnItemStack(itemConfig, pxRpgPlayer, null, resolver);
        if (adapterItemStack == null) {
            return null;
        }
        return MAPI.getBukkitPxRpgAPI().toBukkitItemStack(adapterItemStack);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        AdapterItemStack pxRpgItemStack = MAPI.getBukkitPxRpgAPI().toPxRpgItemStack(item);
        if (pxRpgItemStack == null) {
            return Optional.empty();
        }
        ItemInter that = itemManager.toThat(pxRpgItemStack);
        if (that == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(that.getId());
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        AdapterItemStack pxRpgItemStack = MAPI.getBukkitPxRpgAPI().toPxRpgItemStack(item);
        if (pxRpgItemStack == null) {
            return null;
        }
        ItemInter that = itemManager.toThat(pxRpgItemStack);
        if (that == null) {
            return null;
        }
        return that.getId();
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return id(item).isPresent();
    }

    @Override
    public boolean has(@NotNull String id) {
        String[] split = id.split(",", 2);
        if (split.length < 1) {
            return false;
        }
        return itemManager.getRegister(split[0]) != null;
    }
}
