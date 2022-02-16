@file:Suppress("unused")

package cn.novacoo.mirai

import org.gradle.api.Project

class Property(private val project: Project) {
    fun has(key: String): Boolean = project.hasProperty(key)
    operator fun get(key: String): Any? = project.property(key)
    inline fun ifPresent(key: String, block: (String) -> Unit) {
        if (has(key)) block(key)
    }
}

private val propDelegate = hashMapOf<Project, Property>()

val Project.prop: Property
    get() = propDelegate.getOrPut(this) { Property(this) }

val Project.rootProp: Property
    get() = rootProject.prop

val env: Map<String, String>
    get() = System.getenv()

inline fun <T> Iterable<T>.applyEach(action: T.() -> Unit) {
    forEach(action)
}

inline fun <K, V> Map<out K, V>.applyEach(action: (Map.Entry<K, V>) -> Unit) {
    forEach(action)
}

inline fun <T> List<T>.applyEach(action: T.() -> Unit) {
    forEach(action)
}

inline fun <T> Collection<T>.applyEach(action: T.() -> Unit) {
    forEach(action)
}

inline fun <T> Set<T>.applyEach(action: T.() -> Unit) {
    forEach(action)
}
