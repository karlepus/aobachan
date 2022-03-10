@file:Suppress("unused")

package karlepus.aobachan.listener

import karlepus.aobachan.AobaChan
import karlepus.aobachan.mc.teacon.ChaHouTanSubscriber
import kotlinx.coroutines.CancellationException
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.ExceptionInEventHandlerException
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import kotlin.coroutines.CoroutineContext

/**
 * 青叶酱主要事件监听器。
 *
 * @author KarLepus
 */
internal object AobaChanListener : SimpleListenerHost() {
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        when (exception) {
            is ExceptionInEventHandlerException ->
                AobaChan.logger.warning("青叶酱主要事件监听器处理异常", exception.cause)
            is CancellationException -> AobaChan.logger.warning("青叶酱主要事件监听器发生异常", exception)
        }
    }

    @EventHandler
    suspend fun GroupMessageEvent.handle() {
        val chaHouTanSubscriber = ChaHouTanSubscriber()
        chaHouTanSubscriber.rss()
        val data = chaHouTanSubscriber.latest()
        subject.sendMessage(buildString {
            append(data.title).append('\n')
            append(data.description).append('\n')
            append(data.link).append('\n')
            append(data.publish).append('\n')
            append(data.issues)
        })
        println(data.image)
    }
}
