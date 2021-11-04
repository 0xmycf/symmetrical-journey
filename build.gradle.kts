plugins {
    id("fabric-loom") version "0.10-SNAPSHOT"
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm") version kotlinVersion
}
base {
    val archivesBaseName: String by project
    archivesName.set(archivesBaseName)
}

val javaVersion = JavaVersion.VERSION_16.toString()
val modVersion: String by project
val mavenGroup: String by project
val minecraftVersion: String by project
val yarnMappings: String by project
val loaderVersion: String by project
val fabricVersion: String by project
val fabricKotlinVersion: String by project

version = modVersion
group = mavenGroup

minecraft {}
repositories {}

dependencies {

    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnMappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")

    // FAPI
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")

    // Kotlin Language Adapter
    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
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
        sourceCompatibility = JavaVersion.VERSION_16
        targetCompatibility = JavaVersion.VERSION_16
        withSourcesJar()
    }
}