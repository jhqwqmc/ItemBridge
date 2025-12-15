package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.hook.context.ItemContextKeys;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.sobject.SObjectInterface;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ExecutableItemsProvider implements Provider<ItemStack> {
    public static final ExecutableItemsProvider INSTANCE = new ExecutableItemsProvider();

    private ExecutableItemsProvider() {}

    @Override
    public String plugin() {
        return "executableitems";
    }

    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
        return ExecutableItemsAPI.getExecutableItemsManager()
                .getExecutableItem(id)
                .map(i -> i.buildItem(1, context.get(ItemContextKeys.PLAYER)));
    }

    @Override
    public Optional<String> id(ItemStack item) {
        return ExecutableItemsAPI.getExecutableItemsManager()
                .getExecutableItem(item)
                .map(SObjectInterface::getId);
    }

    @Override
    public boolean is(ItemStack item) {
        return ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(item).isPresent();
    }

    @Override
    public boolean has(String id) {
        return ExecutableItemsAPI.getExecutableItemsManager().isValidID(id);
    }
}
