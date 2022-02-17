@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.6.10"
    java
    `kotlin-dsl`
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("net.mamoe.mirai-console") version "2.10.0"
}

group = "cn.novacoo.mirai"
version = "1.0.0"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.RequiresOptIn")
}

mirai {
    coreVersion = "2.10.0"
}

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    implementation("com.google.auto.service:auto-service:1.0.1")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    withType<Javadoc> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}
