@file:JvmName("AobaChan")
@file:Suppress("unused")

package karlepus.aobachan

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi

object AobaChan : KotlinPlugin(
    JvmPluginDescription(
        "karlepus.aobachan",
        "1.0.0",
        "AobaChan",
    ) {
        author("KarLepus")
        info("""一个以 Minecraft 为主题的基于 Mirai Console 的 QQ 机器人插件""")
    }
) {
    override fun onEnable() {
    }

    override fun onDisable() {
    }

    @OptIn(ConsoleExperimentalApi::class)
    override val autoSaveIntervalMillis: LongRange
        get() = LongRange(200, 400)
}
