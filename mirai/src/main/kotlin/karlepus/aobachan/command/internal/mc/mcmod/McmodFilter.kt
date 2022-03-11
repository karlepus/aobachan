package karlepus.aobachan.command.internal.mc.mcmod

import karlepus.aobachan.command.internal.InternalCommandValueArgumentParserExtensions
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.descriptor.CommandValueArgumentParser

/**
 * [MC百科|最大的Minecraft中文MOD百科](https://www.mcmod.cn/) 与搜索页面搜索栏下的可选项目对应的搜索过滤器。
 *
 * @param identifier 命令需要的参数 ID。
 * @param i18n 命令需要的中文名字。
 * @author KarLepus
 */
internal enum class McmodFilter(
    private val identifier: String,
    private val i18n: String
) {
    /**
     * 搜索全部。
     */
    ALL("all", "全部"),

    /**
     * 仅搜索模组。
     */
    MOD("mod", "模组"),

    /**
     * 仅搜索整合包。
     */
    MODPACK("modpack", "整合包"),

    /**
     * 仅搜索资料。
     */
    MATERIAL("material", "资料"),

    /**
     * 仅搜索教程。
     */
    TUTORIAL("tutorial", "教程"),

    /**
     * 仅搜索作者信息。
     */
    AUTHOR("author", "作者"),

    /**
     * 仅搜索用户信息。
     */
    USER("user", "用户"),

    /**
     * 仅搜索社群。
     */
    BBS("bbs", "社群"),

    /**
     * 仅搜索服务器。
     */
    SERVER("server", "服务器");

    /**
     * 用于自定义本枚举类型作为命令参数时的解析器，以让作为命令参数时得到正确得解析。
     */
    internal object Parser : CommandValueArgumentParser<McmodFilter>,
        InternalCommandValueArgumentParserExtensions<McmodFilter>() {
        override fun parse(raw: String, sender: CommandSender): McmodFilter {
            val key: McmodFilter? = values().find {
                it.name.equals(raw, false)
                        || it.identifier.equals(raw, false)
                        || it.i18n.contentEquals(raw)
            }
            return key ?: illegalArgument(buildString {
                append("🍀====== 模组百科 ======🍀\n")
                append("❌过滤器参数错误~\n\n")
                append("🔥正确参数如下：\n")
                values().forEach { append("💠").appendBlank(it.identifier, it.i18n) }
            }.trim())
        }

        /**
         * 拼接适当长度的空格控制格式。
         */
        private fun StringBuilder.appendBlank(id: String, i8: String): StringBuilder = this.let {
            val len: Int = 10 - id.length
            var p = ""
            for (i in 0..len) p += " "
            append(id.lowercase()).append(p).append(i8).append('\n')
        }
    }
}
