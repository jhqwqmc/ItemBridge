dependencies {
    compileOnly(libs.platform.paper.j21)
    compileOnly(libs.bundles.hooks.legacy) { isTransitive = false }
    compileOnly(project(":api"))
}

tasks.withType<JavaCompile> {
    options.release.set(16) // 需要访问记录类
}
