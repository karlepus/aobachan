@file:Suppress("unused")

package karlepus.aobachan.scheduler

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

internal object DailyNewScheduler : CoroutineScope {
    override lateinit var coroutineContext: CoroutineContext
}
