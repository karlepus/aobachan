@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    group = "io.karlepus.mirai"
    version = "1.0.0"

    extensions.configure(KotlinJvmProjectExtension::class) {
        explicitApi()

        sourceSets.all {
            languageSettings {
                optIn("koltin.RequiresOptIn")
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
                progressiveMode = true
            }
        }
    }

    repositories {
        maven("https://maven.aliyun.com/repository/public/")
        mavenCentral()
    }
}
