plugins {
    id("java")
}

dependencies {
    compileOnly(libs.platform.paper.legacy)
    compileOnly(libs.kotlin.stdlib) // ratziel need
    compileOnly(libs.bundles.hooks) { isTransitive = false }
    compileOnly(files("${project.rootDir}/libs/SCore-api.jar")) // executableitems
    compileOnly(files("${project.rootDir}/libs/AzureFlow-api.jar")) // azureflow
    compileOnly(files("${project.rootDir}/libs/MagicGem-api.jar")) // magicgem
    compileOnly(files("${project.rootDir}/libs/PxRpg-api.jar")) // pxrpg
    compileOnly(files("${project.rootDir}/libs/Ratziel-api.jar")) // ratziel
    compileOnly(project(":api"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(17)
    dependsOn(tasks.clean)
}
