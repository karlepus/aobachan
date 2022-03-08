@file:JvmName("AobaChan")
@file:Suppress("unused")

package io.karlepus.aobachan

import io.karlepus.aobachan.command.GameCommands
import io.karlepus.aobachan.api.http
import io.karlepus.aobachan.command.MinecraftCommands
import io.karlepus.aobachan.setting.config.AobaChanAllConfigHandler
import io.karlepus.aobachan.setting.data.AobaChanAllDataHandler
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import okhttp3.internal.closeQuietly
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
        // 重载配置和数据
        AobaChanAllConfigHandler.reloadAll()
        AobaChanAllDataHandler.reloadAll()
        // 注册所有命令
        GameCommands.registerAll()
        MinecraftCommands.registerAll()
    }

    override fun onDisable() {
        http.closeQuietly()
        // 注销所有命令
        GameCommands.unregisterAll()
        MinecraftCommands.unregisterAll()
    }

    @OptIn(ConsoleExperimentalApi::class)
    override val autoSaveIntervalMillis: LongRange
        get() = LongRange(200, 400)

    /**
     * `Sakura - 樱酥体 蔷薇微光静谧时`
     */
    internal val sakura: Font by lazy {
        this::class.java.classLoader.getResourceAsStream("sakura.ttf").use {
            Font.createFont(Font.TRUETYPE_FONT, it).deriveFont(0).deriveFont(13f)
        }
    }
}
