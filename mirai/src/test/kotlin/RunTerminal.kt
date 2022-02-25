@file:Suppress("SpellCheckingInspection")
@file:JvmName("MiraiConsoleTerminal")

package cn.novacoo.mirai

import karlepus.aobachan.AobaChan
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import java.io.File

fun setupWorkingDir() {
    // see: net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal
    System.setProperty("user.dir", File("debug-sandbox").absolutePath)
}

@OptIn(ConsoleExperimentalApi::class)
suspend fun main() {
    setupWorkingDir()
    MiraiConsoleTerminalLoader.startAsDaemon()
    AobaChan.load() // 主动加载插件, Console 会调用 AobaChan.onLoad
    AobaChan.enable() // 主动启用插件, Console 会调用 AobaChan.onEnable
    MiraiConsole.job.join()
}
