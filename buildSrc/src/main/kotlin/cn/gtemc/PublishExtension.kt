package cn.gtemc

import org.gradle.api.publish.maven.MavenPublication

open class PublishExtension {
    open fun applyCommonPom(pub: MavenPublication, customName: String) {
        pub.pom {
            name.set(customName)
            url.set("https://github.com/jhqwqmc/ItemBridge")
            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://opensource.org/licenses/MIT")
                    distribution.set("repo")
                }
            }
        }
    }
}
