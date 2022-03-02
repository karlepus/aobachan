@file:JvmName("AobaChan")
@file:Suppress("unused")

package io.karlepus.aobachan

import io.karlepus.aobachan.command.GamesCommands
import io.karlepus.aobachan.setting.config.AobaChanAllConfigHandler
import io.karlepus.aobachan.setting.data.AobaChanAllDataHandler
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import java.awt.Font

public object AobaChan : KotlinPlugin(
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
        // 重载配置
        AobaChanAllConfigHandler.reloadAll()
        AobaChanAllDataHandler.reloadAll()
        // 注册命令
        GamesCommands.registerAll()
    }

    override fun onDisable() {
    }

    @OptIn(ConsoleExperimentalApi::class)
    override val autoSaveIntervalMillis: LongRange
        get() = LongRange(200, 400)

    /**
     * 樱花体。
     */
    internal val sakura: Font = Font.createFont(
        Font.TRUETYPE_FONT,
        Thread.currentThread().contextClassLoader.getResourceAsStream("sakura.ttf")
    )
}
