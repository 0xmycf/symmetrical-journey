plugins {
    id("fabric-loom") version "0.10-SNAPSHOT"
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm") version kotlinVersion
}
base {
    val archivesBaseName: String by project
    archivesName.set(archivesBaseName)
}

val javaVersion = JavaVersion.VERSION_17.toString()
val modVersion: String by project
val mavenGroup: String by project
val minecraftVersion: String by project
val yarnMappings: String by project
val loaderVersion: String by project
val fabricVersion: String by project
val kotlinVersion: String by System.getProperties()

version = modVersion
group = mavenGroup

minecraft {}
repositories {}

// dependency: kotlin(a,b) | String
fun DependencyHandlerScope.includeModApi(dependency: Any) {
    modApi(dependency)
    include(dependency)
}

dependencies {

    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnMappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")

    // FAPI
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")

    // Kotlin libs
    includeModApi(kotlin("stdlib", kotlinVersion))
    includeModApi(kotlin("stdlib-jdk8", kotlinVersion))
    includeModApi(kotlin("stdlib-jdk7", kotlinVersion))
    includeModApi(kotlin("reflect", kotlinVersion))
    includeModApi("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    includeModApi("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.5.2")
    includeModApi("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.5.2")
    // something wrong here
//    includeModApi(kotlin("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.3.0"))
//    includeModApi(kotlin("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.3.0"))
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        targetCompatibility = javaVersion
        sourceCompatibility = javaVersion
        options.release.set(javaVersion.toInt())
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions { jvmTarget = javaVersion }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    jar { from("LICENSE") { rename { "${it}_${base.archivesName}" } } }
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") { expand("version" to project.version) }
    }
    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(javaVersion)) }
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        withSourcesJar()
    }
}