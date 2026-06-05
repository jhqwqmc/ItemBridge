import cn.gtemc.PublishExtension

plugins {
    id("java")
}

subprojects {
    apply {
        plugin("java")
    }

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") // paper
        maven("https://hub.spigotmc.org/nexus/content/groups/public/") // spigot
        maven("https://repo.momirealms.net/releases/") // craftengine customfishing
        maven("https://repo.momirealms.net/snapshots/") // craftengine
        maven("https://maven.devs.beer/") // itemsadder
        maven("https://repo.nexomc.com/releases/") // nexo
        maven("https://nexus.phoenixdevt.fr/repository/maven-public/") // mmoitems
        maven("https://mvn.lumine.io/repository/maven-public/") // mythicmobs
        maven("https://r.irepo.space/maven/") // neigeitems
        maven("https://jitpack.io") // sxitem | slimefun | executableitems | executableblocks | itemedit
        maven("https://repo.oraxen.com/releases") // oraxen
        maven("https://repo.xenondevs.xyz/releases") // nova
        maven("https://repo.auxilor.io/repository/maven-public/") // eco plugins
        maven("https://repo.aeoliancloud.com/repository/releases") // baikiruto
        maven("https://repo.crazycrew.us/releases") // crazyvouchers
        maven("https://erethon.de/repo/") // itemxl
        maven("https://repo.rosewooddev.io/repository/public/") // advanceditems
        maven("https://artifacts.wolfyscript.com/artifactory/gradle-dev-local/") // customcrafting
    }

    extensions.create<PublishExtension>("publication")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
        withSourcesJar()
        disableAutoTargetJvm()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:-options"))
        dependsOn(tasks.clean)
    }

}
