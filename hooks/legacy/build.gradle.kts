dependencies {
    compileOnly(libs.platform.paper.j21)
    compileOnly(libs.bundles.hooks.legacy) { isTransitive = false }
    compileOnly(files("${project.rootDir}/libs/j8-record.jar"))
    compileOnly(project(":api"))
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
