import java.io.ByteArrayOutputStream

val shade: Configuration by configurations.creating

plugins {
    java
    application
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    id("org.graalvm.buildtools.native") version "0.10.2"
}

group = "ru.meproject"
version = "1.0.0-RC.18"

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
    shade("org.slf4j:slf4j-nop:1.7.32")
    shade("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}


tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(
        shade.map {
            if (it.isDirectory) it else zipTree(it)
        }
    )
}

application {
    mainClass = "ru.meproject.pterocli.PterocliKt"
}

java {
    manifest {
        attributes(
            "Main-Class" to "ru.meproject.pterocli.PterocliKt"
        )
    }
}

graalvmNative {
    testSupport = false
    metadataRepository {
        enabled = true
    }
    agent {
        enabled = true
        metadataCopy {
            inputTaskNames.add("test")
            outputDirectories.add("src/main/resources/META-INF/native-image/ru/meproject/pterocli")
            mergeWithExisting = true
        }
    }

    binaries {
        named("main") {
            mainClass = "ru.meproject.pterocli.PterocliKt"
            buildArgs.addAll("--enable-sbom")
            runtimeArgs.addAll("-Xmx512M")
        }
    }
}

fun isWindows(): Boolean = System.getProperty("os.name").lowercase().contains("win")

fun gitShortRev(): String {
    val outputStream = ByteArrayOutputStream()
    exec {
        if (isWindows()) {
            commandLine("cmd", "/c", "git rev-parse --short HEAD")
        } else {
            commandLine("bash", "-c", "git rev-parse --short HEAD")
        }
        standardOutput = outputStream
    }
    return outputStream.toString().trim()
}

tasks.register<Copy>("generateTemplates") {
    inputs.properties("version" to version, "gitRev" to gitShortRev())
    from(file("src/main/templates"))
    into(layout.buildDirectory.dir("generated/sources/templates"))
    expand("version" to version, "gitRev" to gitShortRev())
}

sourceSets.main {
    java.srcDir(tasks.named("generateTemplates").map { it.outputs.files })
}

tasks.compileKotlin {
    dependsOn(tasks.named("generateTemplates"))
}


