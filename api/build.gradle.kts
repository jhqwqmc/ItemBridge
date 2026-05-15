plugins {
    id("itembridge-publish")
}

dependencies {
    compileOnly(libs.jetbrains.annotations)
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "cn.gtemc"
            artifactId = "itembridge-api"
            version = rootProject.properties["project_version"].toString()
            from(components["java"])
            publication.applyCommonPom(this, "ItemBridge-API")
        }
    }
}
