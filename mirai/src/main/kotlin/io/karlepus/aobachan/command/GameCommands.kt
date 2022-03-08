@file:Suppress("unused", "SpellCheckingInspection")

package io.karlepus.aobachan.command

import io.karlepus.aobachan.AobaChan
import io.karlepus.aobachan.command.internal.AobaChanCommandInternals
import kotlinx.coroutines.cancel
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.permission.Permission
import net.mamoe.mirai.console.permission.PermissionService
import kotlin.concurrent.thread

/**
 * 所有游戏命令都在此处被 `注册` 和 `注销` 。
 *
 * @author KarLepus
 */
internal object GameCommands {
    /**
     * 父级权限。
     */
    internal val parentPermission: Permission by lazy {
        PermissionService.INSTANCE.register(
            AobaChan.permissionId("game"),
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
        GameCommands::class.nestedClasses.forEach { (it.objectInstance as? Command)?.register() }
    }

    object WordleCommand : SimpleCommand(
        AobaChan,
        "wordle",
        description = "青叶酱 Wordle 填词游戏",
        parentPermission = parentPermission
    ), AobaChanCommandInternals {
        @Handler
        suspend fun CommandSenderOnMessage<*>.handle() {
            when (this) {
                is MemberCommandSenderOnMessage,
                is OtherClientCommandSenderOnMessage -> return
                else -> {
                    sendMessage("准备好哦~开始今天的挑战吧~\uD83D\uDFE9")
                }
            }
        }
    }
}
