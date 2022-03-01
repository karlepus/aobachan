@file:Suppress("unused")

package io.karlepus.aobachan.setting.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * 青叶酱插件基本配置。
 *
 * @author KarLepus
 */
internal object AobaChanConfig : AutoSavePluginConfig("aobachan") {
    @ValueDescription("插件是否启用，设置为 false 时禁用插件。[default: true]")
    val enabled: Boolean by value(true)

    @ValueDescription("是否在命令参数不匹配时输出命令帮助？设置为 false 关闭帮助。[default: true]")
    val replyUnresolvedCommandHelp: Boolean by value(true)
}
