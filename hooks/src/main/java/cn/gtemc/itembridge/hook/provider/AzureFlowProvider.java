package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.util.MiscUtils;
import io.rokuko.azureflow.api.AzureFlowAPI;
import io.rokuko.azureflow.api.item.Item;
import io.rokuko.azureflow.api.item.ItemFactory;
import io.rokuko.azureflow.features.item.factory.AzureFlowItemFactory;
import io.rokuko.azureflow.features.item.factory.AzureFlowItemFactoryService;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class AzureFlowProvider implements Provider<ItemStack, Player> {
    public static final AzureFlowProvider INSTANCE = new AzureFlowProvider();

    private AzureFlowProvider() {}

    @Override
    public String plugin() {
        return "azureflow";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        ItemFactory<ItemStack, Player, Location> factory = AzureFlowAPI.INSTANCE.getFactory(id);
        if (factory == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(factory.build().itemStack(player, MiscUtils.adaptString2Object(context)));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        ItemFactory<ItemStack, Player, Location> factory = AzureFlowAPI.INSTANCE.getFactory(id);
        if (factory == null) {
            return null;
        }
        return factory.build().itemStack(player, MiscUtils.adaptString2Object(context));
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        Item<ItemStack, Player, Location> azureFlowItem = AzureFlowAPI.INSTANCE.toItem(item);
        if (azureFlowItem == null) {
            return Optional.empty();
        }
        ItemFactory<ItemStack, Player, Location> factory = azureFlowItem.getFactory();
        for (Map.Entry<String, AzureFlowItemFactory> entry : AzureFlowItemFactoryService.INSTANCE.getContainer().entrySet()) {
            if (entry.getValue().equals(factory)) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        Item<ItemStack, Player, Location> azureFlowItem = AzureFlowAPI.INSTANCE.toItem(item);
        if (azureFlowItem == null) {
            return null;
        }
        ItemFactory<ItemStack, Player, Location> factory = azureFlowItem.getFactory();
        for (Map.Entry<String, AzureFlowItemFactory> entry : AzureFlowItemFactoryService.INSTANCE.getContainer().entrySet()) {
            if (entry.getValue().equals(factory)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return AzureFlowAPI.INSTANCE.toItem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return AzureFlowAPI.INSTANCE.getFactory(id) != null;
    }
}
