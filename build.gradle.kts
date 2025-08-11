plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25" apply false
    kotlin("plugin.jpa") version "1.9.25" apply false
    id("org.springframework.boot") version "3.5.4" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

group = "com.door"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "java-test-fixtures")

    repositories {
        mavenCentral()
    }

    dependencies {
        val kotestVersion = "5.9.1"

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        testImplementation("org.junit.jupiter:junit-jupiter-engine")
        testImplementation ("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly ("org.junit.platform:junit-platform-launcher")

        // mockk
        testImplementation("com.ninja-squad:springmockk:4.0.2")
        testImplementation("io.mockk:mockk:1.13.8")

        // kotest
        testImplementation ("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation ("io.kotest:kotest-assertions-core:$kotestVersion")
        testImplementation ("io.kotest:kotest-property:$kotestVersion")
        testImplementation ("io.kotest:kotest-framework-datatest:$kotestVersion")

        // jackson
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.19.0")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    kotlin {
        jvmToolchain(17)
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
