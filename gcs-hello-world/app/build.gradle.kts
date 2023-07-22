plugins {
    application
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
}

repositories {
    mavenCentral()
}

val junitJupiterVersion = "5.9.2"
val gcsVersion = "2.23.0"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.google.cloud:google-cloud-storage:$gcsVersion")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

application {
    mainClass.set("gcs.hello.world.App")
}

version = gcsVersion

tasks.shadowJar {
    archiveClassifier = ""
    archiveVersion = version.toString()
    manifest {
        attributes["Main-Class"] = "gcs.hello.world.App"
    }

    // List of packages to be relocated
    val packagesToRelocate = listOf("io", "javax/annotation", "org", "android", "com")

    packagesToRelocate.forEach { pkg ->
        relocate(pkg, "shade/$pkg") {
            // expose these public interface classes
            exclude("%regex[com/google/cloud.*]")
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.shadowJar.get()) {
                builtBy(tasks.shadowJar)
            }

            groupId = "com.shaded"
            artifactId = "gcs"
            version = gcsVersion
        }
    }
    repositories {
        mavenLocal()
    }
}