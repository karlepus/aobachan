@file:JvmName("AobaChan")
@file:Suppress("unused")

package karlepus.aobachan

import karlepus.aobachan.api.http
import karlepus.aobachan.command.MinecraftCommands
import karlepus.aobachan.listener.AobaChanAllListenerHandler
import karlepus.aobachan.scheduler.AobaChanSchedulers
import karlepus.aobachan.setting.config.AobaChanAllConfigHandler
import karlepus.aobachan.setting.data.AobaChanAllDataHandler
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
        MinecraftCommands.registerAll()
        // 注册所有事件监听器
        AobaChanAllListenerHandler.registerAll()
        // 启动所有任务
        AobaChanSchedulers.startAll()
    }

    override fun onDisable() {
        http.closeQuietly()
        // 注销所有命令
        MinecraftCommands.unregisterAll()
        // 停止所有事件监听器
        AobaChanAllListenerHandler.cancelAll()
    }

    @OptIn(ConsoleExperimentalApi::class)
    override val autoSaveIntervalMillis: LongRange
        get() = LongRange(200, 400)

    /**
     * `Sakura - 樱酥体 蔷薇微光静谧时` （普通，默认 `13` 号字体大小）。
     */
    internal val sakura: Font by lazy {
        this::class.java.classLoader.getResourceAsStream("sakura.ttf").use {
            Font.createFont(Font.TRUETYPE_FONT, it).deriveFont(0).deriveFont(13f)
        }
    }
}
