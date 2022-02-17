@file:JvmName("AutoLoad")
@file:Suppress("unused")

package cn.novacoo.mirai.annotation

import java.lang.annotation.Inherited

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE)
@Inherited
annotation class AutoLoad
