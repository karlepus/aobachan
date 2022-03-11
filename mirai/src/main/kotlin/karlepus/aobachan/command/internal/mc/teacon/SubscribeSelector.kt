@file:Suppress("unused")

package karlepus.aobachan.command.internal.mc.teacon

import karlepus.aobachan.command.internal.InternalCommandValueArgumentParserExtensions
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.descriptor.CommandValueArgumentParser

/**
 * [TeaCon 模组开发茶会](https://www.teacon.cn) 订阅选择器。
 *
 * - 用于开关某群的 [TeaCon 茶后谈](https://www.teacon.cn/chahoutan/) 订阅功能的命令参数。
 *
 * @author KarLepus
 */
internal enum class SubscribeSelector(
    private val identifier: String,
    private val boolean: String,
    private val i18n: String
) {
    /**
     * 关闭。
     */
    FALSE("false", "0", "关"),

    /**
     * 开启。
     */
    TRUE("true", "1", "开");

    /**
     * 用于自定义本枚举类型作为命令参数时的解析器，以让作为命令参数时得到正确得解析。
     */
    internal object Parser : CommandValueArgumentParser<SubscribeSelector>,
        InternalCommandValueArgumentParserExtensions<SubscribeSelector>() {
        override fun parse(raw: String, sender: CommandSender): SubscribeSelector {
            val key: SubscribeSelector? = values().find {
                it.name.equals(raw, false)
                        || it.identifier.equals(raw, false)
                        || it.boolean.equals(raw, false)
                        || it.i18n.contentEquals(raw)
            }
            return key ?: illegalArgument(buildString {
                append("🍀====== TeaCon茶后谈 ======🍀\n")
                append("❌茶后谈订阅状态命令参数错误~\n\n")
                append("🔥正确参数如下：\n")
                values().forEach { append("💠").appendBlank(it.identifier, it.boolean, it.i18n) }
            }.trim())
        }

        /**
         * 拼接适当长度的空格控制格式。
         */
        private fun StringBuilder.appendBlank(id: String, b: String, i8: String): StringBuilder = this.let {
            val p: String = if (id.lowercase() == "true") " " else ""
            append(id.lowercase()).append("$p   ").append(b.lowercase()).append("   ").append(i8).append('\n')
        }
    }
}
