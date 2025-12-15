plugins {
    id("java")
}

dependencies {
    compileOnly(libs.platform.paper)
    compileOnly(libs.bundles.hooks.j21) { isTransitive = false }
    compileOnly(files("${project.rootDir}/libs/HMCCosmetics-api.jar")) // hmccosmetics
    compileOnly(project(":api"))
    compileOnly(project(":hooks"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(21)
    dependsOn(tasks.clean)
}
