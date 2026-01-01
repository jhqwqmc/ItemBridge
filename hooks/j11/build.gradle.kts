plugins {
    id("java")
}

dependencies {
    compileOnly(libs.jetbrains.annotations)
    compileOnly(libs.platform.spigot.j8)
    compileOnly(libs.bundles.hooks.j11) { isTransitive = false }
    compileOnly(project(":api"))
    compileOnly(project(":hooks"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(11)
    dependsOn(tasks.clean)
}
