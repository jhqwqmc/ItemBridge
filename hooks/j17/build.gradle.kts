plugins {
    id("java")
}

dependencies {
    compileOnly(libs.platform.paper.j17)
    compileOnly(libs.bundles.hooks.j17) { isTransitive = false }
    compileOnly(project(":api"))
    compileOnly(project(":hooks"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(17)
    dependsOn(tasks.clean)
}
