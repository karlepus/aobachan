@file:Suppress("unused")

package karlepus.aobachan.scheduler.mc.teacon

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

internal object ChaHouTanSubscribeScheduler : CoroutineScope {
    override lateinit var coroutineContext: CoroutineContext
}
