@file:Suppress("SpellCheckingInspection")

plugins {
    java
}

group = "karlepus.mirai"
version = "1.0.0"

subprojects {
    apply(plugin = "java")

    repositories {
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.release.set(17)
        }

        withType<Javadoc> {
            options.encoding = "UTF-8"
        }
    }
}
