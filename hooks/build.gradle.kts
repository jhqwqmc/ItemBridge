dependencies {
    compileOnly(libs.jetbrains.annotations)
    compileOnly(libs.platform.paper.j21)
    compileOnly(libs.bundles.hooks) { isTransitive = false }
    compileOnly(files("${project.rootDir}/libs/HMCCosmetics-api.jar")) // hmccosmetics
    compileOnly(files("${project.rootDir}/libs/Sertraline-api.jar")) // sertraline
    compileOnly(files("${project.rootDir}/libs/AzureFlow-api.jar")) // azureflow
    compileOnly(files("${project.rootDir}/libs/MagicGem-api.jar")) // magicgem
    compileOnly(files("${project.rootDir}/libs/PxRpg-api.jar")) // pxrpg
    compileOnly(files("${project.rootDir}/libs/Ratziel-api.jar")) // ratziel
    compileOnly(files("${project.rootDir}/libs/DragonArmourers-api.jar")) // dragonarmourers
    compileOnly(files("${project.rootDir}/libs/Zaphkiel-api.jar")) // zaphkiel
    compileOnly(files("${project.rootDir}/libs/j8-record.jar"))
    compileOnly(project(":api"))
    implementation(project(":hooks:legacy"))
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
