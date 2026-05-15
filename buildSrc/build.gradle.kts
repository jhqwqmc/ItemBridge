plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.gradleup.shadow:shadow-gradle-plugin:9.4.1")
    implementation("io.github.valtechmobility:gradle-credentials-onepassword:0.1.0")
}
