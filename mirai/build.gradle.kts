@file:Suppress("SpellCheckingInspection")

plugins {
    id("net.mamoe.mirai-console") version "2.10.0"
}

mirai {
    coreVersion = "2.10.0"
}

dependencies {
    implementation(project(":games"))
    implementation(project(":minecraft"))
}
