@file:Suppress("unused", "SpellCheckingInspection")

import org.gradle.api.Project

class Property internal constructor(private val project: Project) {
    operator fun get(key: String): String = project.findProperty(key)?.toString()
        ?: error("no key-value on properties found")
}

private val propDelegate = hashMapOf<Project, Property>()

val Project.prop: Property
    get() = propDelegate.getOrPut(this) { Property(this) }

val Project.rootProp: Property
    get() = rootProject.prop

val Project.thumbnailator: String
    get() = rootProp["thumbnailator"]

val Project.ktor: String
    get() = rootProp["ktor-client-okhttp"]

val Project.serialization: String
    get() = rootProp["kotlinx-serialization"]
