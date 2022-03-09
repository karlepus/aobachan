@file:Suppress("unused")

package karlepus.aobachan.setting.config

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
}
