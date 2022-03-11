@file:Suppress("unused")

package karlepus.aobachan.scheduler

import karlepus.aobachan.scheduler.mc.teacon.ChaHouTanSubscribeScheduler

internal object AobaChanSchedulers {
    internal fun startAll() {
        ChaHouTanSubscribeScheduler.start()
    }
}
