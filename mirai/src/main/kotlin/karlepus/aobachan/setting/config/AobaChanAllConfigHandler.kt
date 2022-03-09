@file:Suppress("unused")

package karlepus.aobachan.setting.config

import karlepus.aobachan.AobaChan.reload
import karlepus.aobachan.AobaChan.save

/**
 * 所有的 ``
 */
internal object AobaChanAllConfigHandler {
    internal fun reloadAll() {
        AobaChanConfig.reload()
    }

    internal fun saveAll() {
        AobaChanConfig.save()
    }
}
