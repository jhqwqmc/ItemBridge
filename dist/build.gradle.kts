import io.github.valtechmobility.gradle.credentials.onepassword.OnepasswordAccessCredentials

plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.shadowjar)
}

dependencies {
    implementation(project(":api"))
    implementation(project(":core"))
    implementation(project(":hooks"))
}

evaluationDependsOn(":api")
evaluationDependsOn(":core")
evaluationDependsOn(":hooks")
evaluationDependsOn(":hooks:j21")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withSourcesJar()
}

artifacts {
    archives(tasks.shadowJar)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(17)
}

tasks {
    shadowJar {
        from(zipTree(project(":hooks:j21").tasks.jar.get().archiveFile))
        archiveClassifier = ""
        archiveFileName = "${rootProject.name}-${rootProject.properties["project_version"]}.jar"
        destinationDirectory.set(file("$rootDir/target"))
    }
    named<Jar>("sourcesJar") {
        val sourceProjects = listOf(project(":api"), project(":core"), project(":hooks"), project(":hooks:j21"))
        from(sourceProjects.map { subProject ->
            subProject.the<JavaPluginExtension>().sourceSets.getByName("main").allSource
        })
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
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
            artifactId = "itembridge"
            version = rootProject.properties["project_version"].toString()
            artifact(tasks["sourcesJar"])
            from(components["shadow"])
            pom {
                name = "ItemBridge"
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
