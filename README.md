# ItemBridge

## Supported plugins

- [AzureFlow](https://www.minebbs.com/resources/9673)
- [CraftEngine](https://github.com/Xiao-MoMi/craft-engine)
- [CustomFishing](https://github.com/Xiao-MoMi/Custom-Fishing)
- [EcoArmor](https://github.com/Auxilor/EcoArmor)
- [EcoCrates](https://github.com/Auxilor/EcoCrates)
- [EcoItems](https://github.com/Auxilor/EcoItems)
- [EcoMobs](https://github.com/Auxilor/EcoMobs)
- [EcoPets](https://github.com/Auxilor/EcoPets)
- [EcoScrolls](https://github.com/Auxilor/EcoScrolls)
- [ExecutableItems](https://modrinth.com/plugin/executableitems)
- [HeadDatabase](https://www.spigotmc.org/resources/14280)
- [HMCCosmetics](https://github.com/HibiscusMC/HMCCosmetics)
- [ItemsAdder](https://www.spigotmc.org/resources/73355)
- [MagicGem](https://liyi2015.gitbook.io/magicgem/)
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

### Add dependencies to the project

```kts
repositories {
    maven("https://repo.gtemc.net/releases/")
}
```
```kts
dependencies {
    implementation("cn.gtemc:itembridge:1.0.18")
}
```

### Example code

```java
BukkitItemBridge itemBridge = BukkitItemBridge.builder()
        .onHookSuccess(p -> System.out.println("Hooked " + p))
        .onHookFailure((p, e) -> System.out.println("Failed to hook " + p + ", because " + e.getMessage()))
        .register(new CustomProvider())
        .detectSupportedPlugins()
        .removeById("customplugin")
        .build();

ContextKey<Boolean> fly = ContextKey.of(Boolean.class, "fly");

BuildContext context = BuildContext.builder()
        .with(fly, true)
        .build();

Optional<ItemStack> itemStack = itemBridge.build("pluginname", "itemid", player, context);
```
