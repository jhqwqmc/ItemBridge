package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.hook.context.ItemContextKeys;
import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.drops.DropMetadataImpl;
import io.lumine.mythic.core.items.MythicItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings("resource")
public class MythicMobsProvider implements Provider<ItemStack> {
    public static final MythicMobsProvider INSTANCE = new MythicMobsProvider();

    private MythicMobsProvider() {}

    @Override
    public String plugin() {
        return "mythicmobs";
    }

    @Override
    public Optional<ItemStack> build(String id, @NotNull BuildContext context) {
        Optional<MythicItem> item = MythicBukkit.inst().getItemManager().getItem(id);
        if (item.isEmpty()) {
            return Optional.empty();
        }
        MythicItem mythicItem = item.get();
        return context.get(ItemContextKeys.PLAYER)
                .map(BukkitAdapter::adapt)
                .map(player -> {
                    SkillCaster caster = MythicBukkit.inst().getSkillManager().getCaster(player);
                    DropMetadataImpl meta = new DropMetadataImpl(caster, player);
                    return mythicItem.generateItemStack(meta, 1);
                })
                .map(BukkitAdapter::adapt);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(MythicBukkit.inst().getItemManager().getMythicTypeFromItem(item));
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return MythicBukkit.inst().getItemManager().isMythicItem(item);
    }

    @Override
    public boolean has(@NotNull String id) {
        return MythicBukkit.inst().getItemManager().getItem(id).isPresent();
    }
}
