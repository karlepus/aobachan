@file:JvmName("KTest")

package cn.novacoo.mirai

fun <T> T.get() = this!!::class.java
