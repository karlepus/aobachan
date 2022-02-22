@file:Suppress("SpellCheckingInspection")

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":api"))
}
