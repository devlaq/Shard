plugins {
    kotlin("jvm")
}

group = "com.github.devlaq"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("com.github.Anuken.Arc:arc-core:${properties["library.arc.hash"]}")
    implementation("com.github.Anuken.Mindustry:core:v126.2")
    implementation("com.github.Anuken.Mindustry:server:v126.2")
}

tasks.jar {
    archiveFileName.set("ShardCore.jar")
}