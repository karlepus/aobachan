@file:Suppress("unused")

package io.karlepus.aobachan.setting.config

import io.karlepus.aobachan.AobaChan.reload
import io.karlepus.aobachan.AobaChan.save

internal object AobaChanAllConfigHandlers {
    internal fun reloadAll() {
        AobaChanConfig.reload()
    }

    internal fun saveAll() {
        AobaChanConfig.save()
    }
}
