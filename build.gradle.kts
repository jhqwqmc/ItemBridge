import cn.gtemc.PublishExtension

plugins {
    id("java")
}

subprojects {
    apply(plugin = "java")

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
        maven("https://jitpack.io") // sxitem | slimefun
        maven("https://repo.hiusers.com/releases") // zaphkiel
        maven("https://repo.oraxen.com/releases") // oraxen
        maven("https://repo.xenondevs.xyz/releases") // nova
        maven("https://repo.auxilor.io/repository/maven-public/") // eco plugins
        maven("https://repo.aeoliancloud.com/repository/releases") // baikiruto
    }

    extensions.create<PublishExtension>("publication")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
        withSourcesJar()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:-options"))
        dependsOn(tasks.clean)
    }

    val allowed = listOf("compileClasspath", "runtimeClasspath", "testCompileClasspath", "testRuntimeClasspath")
    plugins.withType<JavaPlugin> {
        configurations.matching {
            it.isCanBeResolved && it.name in allowed
        }.all {
            attributes {
                attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, Int.MAX_VALUE)
            }
        }
    }
}
