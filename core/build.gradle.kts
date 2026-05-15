plugins {
    id("com.gradleup.shadow")
    id("itembridge-publish")
}

dependencies {
    compileOnly(libs.jetbrains.annotations)
    compileOnly(libs.platform.spigot.j8)
    implementation(project(":api"))
    implementation(project(":hooks"))
}

artifacts {
    implementation(tasks.shadowJar)
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}

tasks.named<Jar>("sourcesJar") {
    from(project(":api").sourceSets.main.get().allSource)
    from(project(":hooks").sourceSets.main.get().allSource)
    from(project(":hooks:legacy").sourceSets.main.get().allSource)
}

tasks {
    shadowJar {
        archiveClassifier = ""
        archiveFileName = "${rootProject.name}-${rootProject.properties["project_version"]}.jar"
        destinationDirectory.set(file("$rootDir/target"))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "cn.gtemc"
            artifactId = "itembridge"
            version = rootProject.properties["project_version"].toString()
            from(components["shadow"])
            artifact(tasks["sourcesJar"])
            publication.applyCommonPom(this, "ItemBridge")
        }
    }
}
