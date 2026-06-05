# ItemBridge

## Supported plugins

- [AdvancedItems](https://www.spigotmc.org/resources/110438)
- [AzureFlow](https://www.minebbs.com/resources/9673)
- [Baikiruto](https://github.com/YsGqHY/Baikiruto)
- [CraftEngine](https://github.com/Xiao-MoMi/craft-engine)
- [CrazyVouchers](https://github.com/Crazy-Crew/CrazyVouchers)
- [CustomCrafting](https://github.com/WolfyScript/CustomCrafting)
- [CustomFishing](https://github.com/Xiao-MoMi/Custom-Fishing)
- [DragonArmourers](https://archives.mcbbs.co/read.php?tid=951699)
- [EcoArmor](https://github.com/Auxilor/EcoArmor)
- [EcoCrates](https://github.com/Auxilor/EcoCrates)
- [EcoItems](https://github.com/Auxilor/EcoItems)
- [EcoMobs](https://github.com/Auxilor/EcoMobs)
- [EcoPets](https://github.com/Auxilor/EcoPets)
- [EcoScrolls](https://github.com/Auxilor/EcoScrolls)
- [ExecutableBlocks](https://www.spigotmc.org/resources/93406)
- [ExecutableItems](https://modrinth.com/plugin/executableitems)
- [HeadDatabase](https://www.spigotmc.org/resources/14280)
- [HMCCosmetics](https://github.com/HibiscusMC/HMCCosmetics)
- [ItemEdit](https://github.com/emanondev/ItemEdit)
- [ItemsAdder](https://www.spigotmc.org/resources/73355)
- [ItemsXL](https://github.com/DRE2N/ItemsXL)
- [MagicGem](https://liyi2015.gitbook.io/magicgem)
- [MMOItems](https://gitlab.com/phoenix-dvpmt/mmoitems)
- [MythicMobs](https://mythiccraft.io/index.php?resources/1)
- [NeigeItems](https://github.com/ankhorg/NeigeItems-Kotlin)
- [Nexo](https://polymart.org/product/6901)
- [Nova](http://github.com/xenondevs/Nova)
- [Oraxen](http://github.com/oraxen/oraxen)
- [PxRpg](https://www.pxpmc.com/a/pxrpgfree.html)
- [Ratziel](https://github.com/TheFloodDragon/Ratziel-Beta)
- [Reforges](https://github.com/Auxilor/Reforges)
- [Sertraline](https://github.com/zzzyyylllty/Sertraline-Hydrochloride)
- [Slimefun](https://github.com/Slimefun/Slimefun4)
- [StatTrackers](https://github.com/Auxilor/StatTrackers)
- [SX-Item](https://github.com/Saukiya/SX-Item)
- [Talismans](https://github.com/Auxilor/Talismans)
- [Zaphkiel](https://github.com/TabooLib/zaphkiel)

## Reference Projects

- [AntiGriefLib](https://github.com/Xiao-MoMi/AntiGriefLib)

## How to use

### Add dependency to the project

```kts
repositories {
    maven("https://repo.gtemc.net/releases/")
}
```
```kts
dependencies {
    implementation("cn.gtemc:itembridge:1.0.26")
}
```

### Example code

```java
// Create item bridge
BukkitItemBridge itemBridge = BukkitItemBridge.builder()
        .register(CustomProvider.INSTANCE)
        .detectSupportedPlugins(
                p -> System.out.println("Hooked " + p),
                (p, e) -> System.err.println("Failed to hook " + p + ", because " + e.getMessage()),
                p -> !p.getName().equalsIgnoreCase("MyPlugin")
        )
        .removeById("unusedplugin")
        .immutable(true) // If true, the `register` and `removeById` methods cannot be called after the build.
        .build();

// Build item
Player player = ...;
BuildContext context = BuildContext.builder()
        .with(ContextKeys.ITEM_AMOUNT, 10)
        .build();
@Nullable ItemStack itemStack = itemBridge.buildOrNull("custom", "custom_id", player, context);

// Find item id
ItemStack customItem = ...;
@Nullable String id = itemBridge.idOrNull("custom", customItem);

// Check if item is the item from the custom plugin
ItemStack unknownItem = ...;
boolean isCustomItem = itemBridge.is("custom", unknownItem);

// Check if a plugin contains an item with a specific id
String customItemId = ...;
boolean hasCustomItem = itemBridge.has("custom", customItemId);
```

```java
import cn.gtemc.itembridge.api.context.ContextKey;

public final class ContextKeys {
    private ContextKeys() {}

    public static final ContextKey<Integer> ITEM_AMOUNT = ContextKey.of(Integer.class, "item_amount");
}
```

```java
import cn.gtemc.customitem.api.CustomItemApi;
import cn.gtemc.customitem.bukkit.api.BukkitCustomItemApi;
import cn.gtemc.customitem.bukkit.item.BukkitCustomItem;
import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CustomProvider implements Provider<ItemStack, Player> {
    public static final CustomProvider INSTANCE = new CustomProvider();

    private CustomProvider() {
    }

    @Override
    public String plugin() {
        // According to the interface specifications,
        // the plugin name must be in lowercase here.
        return "custom";
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        BukkitCustomItem customItem = BukkitCustomItemApi.getItem(id);
        if (customItem == null) {
            return null;
        }
        int amount = context.getOrDefault(ContextKeys.ITEM_AMOUNT, 1);
        return customItem.build(player, amount);
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return CustomItemApi.getItemId(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return CustomItemApi.hasItem(item);
    }

    @Override
    public boolean has(@NotNull String id) {
        return CustomItemApi.hasItem(id);
    }
}
```
