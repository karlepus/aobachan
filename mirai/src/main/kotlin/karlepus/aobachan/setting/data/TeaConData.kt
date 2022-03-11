@file:Suppress("SpellCheckingInspection", "unused")

package karlepus.aobachan.setting.data

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

internal object TeaConData : AutoSavePluginData("teacon") {
    @ValueDescription("茶后谈最新期数 [default:0]")
    var latest: Int by value(0)

    @ValueDescription("茶后谈更新状态 [default:false]")
    var status: Boolean by value(false)

    @ValueDescription("群茶后谈订阅状态 [群号：状态] [default:]")
    val subscribe: MutableMap<Long, Boolean> by value(mutableMapOf())
}
