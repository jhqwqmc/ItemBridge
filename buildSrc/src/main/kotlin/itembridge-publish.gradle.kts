import io.github.valtechmobility.gradle.credentials.onepassword.OnepasswordAccessCredentials

plugins {
    `maven-publish`
}

val projectVersion = project.rootProject.property("project_version").toString()
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
    }
}
