@file:Suppress("unused", "DuplicatedCode")

package karlepus.aobachan.command

import karlepus.aobachan.AobaChan
import karlepus.aobachan.command.internal.InternalAobaChanCommands
import karlepus.aobachan.command.internal.mc.mcmod.McmodFilter
import karlepus.aobachan.mc.mcmod.McmodRequester
import karlepus.aobachan.mc.mcmod.McmodResponder
import kotlinx.coroutines.cancel
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.buildCommandArgumentContext
import net.mamoe.mirai.console.permission.Permission
import net.mamoe.mirai.console.permission.PermissionService
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.nextMessageOrNull
import kotlin.concurrent.thread

/**
 * 所有 [我的世界 Minecraft](https://www.minecraft.net/zh-hans) 主题相关功能的命令都在此处被 `注册` 和 `注销` 。
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

    /**
     * 注销所有命令。
     */
    internal fun unregisterAll() {
        MinecraftCommands::class.nestedClasses.forEach { (it.objectInstance as? Command)?.unregister() }
    }

    /**
     * 注册一条名为 `"mcmod"` 的 [SimpleCommand] ，用于搜索 [MC百科|最大的Minecraft中文MOD百科](https://www.mcmod.cn/)
     * 上的资料。
     *
     * - p.s. `"代码有点屎山，有大佬会改善质量跪求改善！！！！"`
     *
     * @author KarLepus
     */
    object McmodCommand : SimpleCommand(
        AobaChan,
        "mcmod",
        description = "查询我的世界模组百科",
        parentPermission = parentPermission,
        overrideContext = buildCommandArgumentContext {
            McmodFilter::class with McmodFilter.Parser
        }
    ), InternalAobaChanCommands {
        /**
         * 搜索 [MC百科|最大的Minecraft中文MOD百科](https://www.mcmod.cn/) 资料命令的实现。
         *
         * - 命令格式： `"</>mcmod <key: 关键词> [filter: 过滤器]"`
         */
        @Handler
        suspend fun CommandSenderOnMessage<*>.handle(key: String, filter: McmodFilter = McmodFilter.ALL) {
            val mcmodRequester = McmodRequester(key, filter.ordinal)
            mcmodRequester.violence() // 暴力爬取资源
            mcmodRequester.official() // 官方接口，这里用作搜索不到结果时随即返回的一条消息 p.s.失败信息都免了，大爱（
            val res: McmodResponder?
            try {
                res = mcmodRequester.serialize()
                // 过滤器如下时，进行另外的处理
                if (filter == McmodFilter.USER || filter == McmodFilter.AUTHOR) throw IllegalStateException()
                // 成功搜索到资料，进行一个基本的结果展示
                if (res != null) {
                    val results: List<McmodResponder.Result> = res.results
                    sendMessage(buildString {
                        append(replayInfo(results[0]))
                        append("🍀====== 其他信息 ======🍀\n")
                        append("🔥共搜索到").append(res.totals)
                        append("条结果").append(res.pages).append("页\n")
                    })
                    if (res.totals > 1) sendMessage(At(fromEvent.sender) + buildString {
                        append("您可以在一分钟内输入1-${res.totals}之间的序号查看本页的其他信息哦~输入非数字")
                        append("或一分钟后都将自动结束搜索呢。")
                    }) else return
                    // 查看未展示的其他结果的实现
                    while (true) {
                        val nextMessage: String? = fromEvent.nextMessageOrNull(60000)?.contentToString()
                        try {
                            if (nextMessage == null) break
                            val num: Int = if (nextMessage.toInt() in 1..res.totals) nextMessage.toInt() else 1
                            sendMessage(replayInfo(results[num - 1]).trim())
                        } catch (ignored: NumberFormatException) {
                            break
                        }
                    }
                }
            } catch (ignored: IndexOutOfBoundsException) {
                // 表示没有搜索到结果，重置法实现随机的错误回复
                sendMessage(buildString {
                    val info: String = McmodRequester("################", filter.ordinal).official().string()
                    append("🍀====== 模组百科 ======🍀\n")
                    append("❌$info")
                })
            } catch (ignored: IllegalStateException) {
                // 表示进行上面的两个特殊过滤器的特殊处理的地方 进行特殊处理的回复
                val info: String = mcmodRequester.string()
                sendMessage(buildString {
                    append("🍀====== 模组百科 ======🍀\n")
                    append("💠").append(info.split("\n")[0]).append('\n')
                    append("💠").append(info.split("\n")[1])
                })
            }
        }

        /**
         * 将太过重复的拼接回复信息的部分拉出来。
         */
        private fun CommandSenderOnMessage<*>.replayInfo(result: McmodResponder.Result): String = buildString {
            append("🍀====== 模组百科 ======🍀\n")
            append("💠").append(result.head).append("\n")
            result.body.split("。").let {
                it.forEachIndexed { i, s -> if (i != it.lastIndex) append("    $s。\n") }
            }
            append("🍀====== 资料来源 ======🍀\n")
            append("${result.link}\n")
            append("🍀====== 更新时间 ======🍀\n")
            Regex("""\d+""").findAll(result.update).let {
                val year: String = it.elementAt(0).value
                val month: String = it.elementAt(1).value
                val day: String = it.elementAt(2).value
                append("             ${year}年${month}月${day}日\n")
            }
        }
    }
}
