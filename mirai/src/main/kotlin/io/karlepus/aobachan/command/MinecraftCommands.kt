@file:Suppress("unused")

package io.karlepus.aobachan.command

import io.karlepus.aobachan.AobaChan
import io.karlepus.aobachan.command.internal.AobaChanCommandInternals
import io.karlepus.aobachan.mc.mcmod.McmodFilter
import io.karlepus.aobachan.mc.mcmod.McmodRequester
import io.karlepus.aobachan.mc.mcmod.McmodResponder
import kotlinx.coroutines.cancel
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.permission.Permission
import net.mamoe.mirai.console.permission.PermissionService
import kotlin.concurrent.thread

/**
 * 所有工具命令都在此处被 `注册` 和 `注销` 。
 *
 * @author KarLepus
 */
@Suppress("SpellCheckingInspection")
internal object MinecraftCommands {
    /**
     * 父级权限。
     */
    internal val parentPermission: Permission by lazy {
        PermissionService.INSTANCE.register(
            AobaChan.permissionId("minecraft"),
            "青叶酱游戏命令父级权限"
        )
    }

    /**
     * 所有继承 [Command] 的内部 `object` 子类都是命令。
     */
    internal val all: Array<out Command> by lazy {
        this::class.nestedClasses.mapNotNull { it.objectInstance as? Command }.toTypedArray()
    }

    init {
        Runtime.getRuntime().addShutdownHook(thread(false) {
            MiraiConsole.cancel()
        })
    }

    /**
     * 注册所有命令。
     */
    internal fun registerAll() {
        MinecraftCommands::class.nestedClasses.forEach { (it.objectInstance as? Command)?.register() }
    }

    object McmodCommand : SimpleCommand(
        AobaChan,
        "mcmod",
        description = "查询我的世界模组百科",
        parentPermission = parentPermission
    ), AobaChanCommandInternals {
        @Handler
        suspend fun CommandSenderOnMessage<*>.handle(key: String, filter: McmodFilter = McmodFilter.ALL) {
            val mcmodRequester = McmodRequester(key, filter.ordinal)
            mcmodRequester.violence()
            val res: McmodResponder? = mcmodRequester.serialize()
            if (res != null) {
                val results: List<McmodResponder.Result> = res.results
                val first: McmodResponder.Result = results[0]
                sendMessage(first.body)
            }
        }
    }
}
