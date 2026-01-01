plugins {
    id("java")
}

dependencies {
    compileOnly(libs.jetbrains.annotations)
    compileOnly(libs.platform.spigot.j8)
    compileOnly(libs.kotlin.stdlib) // ratziel need
    compileOnly(libs.bundles.hooks.j8) { isTransitive = false }
    compileOnly(files("${project.rootDir}/libs/SCore-api.jar")) // executableitems
    compileOnly(files("${project.rootDir}/libs/AzureFlow-api.jar")) // azureflow
    compileOnly(files("${project.rootDir}/libs/MagicGem-api.jar")) // magicgem
    compileOnly(files("${project.rootDir}/libs/PxRpg-api.jar")) // pxrpg
    compileOnly(files("${project.rootDir}/libs/Ratziel-api.jar")) // ratziel
    compileOnly(project(":api"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:-options"))
    options.release.set(8)
    dependsOn(tasks.clean)
}
