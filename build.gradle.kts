
val shade: Configuration by configurations.creating

plugins {
    java
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
}

group = "ru.meproject"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://repo.mattmalec.com/repository/releases")
}

configurations {
    implementation.get().extendsFrom(shade)
    shade.resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
}

dependencies {
    testImplementation(kotlin("test"))
    shade("com.github.ajalt.clikt:clikt:4.4.0")
    shade("com.mattmalec:Pterodactyl4J:2.BETA_141")
    shade("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}


tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(
        shade.map {
            if (it.isDirectory) it else zipTree(it)
        }
    )

    manifest {
        attributes(
            "Main-Class" to "ru.meproject.pterocli.PterocliKt"
        )
    }
}
