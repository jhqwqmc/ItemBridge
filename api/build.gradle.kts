    import io.github.valtechmobility.gradle.credentials.onepassword.OnepasswordAccessCredentials

plugins {
    id("java")
    id("maven-publish")
}

dependencies {
    compileOnly(libs.jetbrains.annotations)
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

val isPublishing = gradle.startParameter.taskNames.any {
    it.contains("publish", ignoreCase = true)
}

publishing {
    repositories {
        maven {
            name = "releases"
            url = uri("https://repo.gtemc.net/releases")
            if (isPublishing) {
                credentials(PasswordCredentials::class) {
                    val accessCredentials = OnepasswordAccessCredentials("Employee", "maven-repo")
                    username = accessCredentials.username
                    password = accessCredentials.password
                }
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "cn.gtemc"
            artifactId = "itembridge-api"
            version = rootProject.properties["project_version"].toString()
            from(components["java"])
            pom {
                name = "ItemBridge-API"
                url = "https://github.com/jhqwqmc/ItemBridge"
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://opensource.org/licenses/MIT"
                        distribution = "repo"
                    }
                }
            }
        }
    }
}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("io.github.valtechmobility:gradle-credentials-onepassword:0.1.0")
    }
}
