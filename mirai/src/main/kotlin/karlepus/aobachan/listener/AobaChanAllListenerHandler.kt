@file:Suppress("unused")

package karlepus.aobachan.listener

import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.registerTo

/**
 * 青叶酱所有事件监听器的公共处理类，提供注册和注销全部的方法。
 *
 * @author KarLepus
 */
internal object AobaChanAllListenerHandler {
    /**
     * 注册所有事件监听器。
     */
    internal fun registerAll(eventChannel: EventChannel<*> = GlobalEventChannel) {
        AobaChanListener.registerTo(eventChannel)
    }

    /**
     * 停止所有事件监听器。
     */
    internal fun cancelAll() {
        AobaChanListener.cancelAll()
    }
}
