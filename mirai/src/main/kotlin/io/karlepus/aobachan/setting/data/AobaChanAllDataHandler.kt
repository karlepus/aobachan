@file:Suppress("unused")

package io.karlepus.aobachan.setting.data

import io.karlepus.aobachan.AobaChan.reload
import io.karlepus.aobachan.AobaChan.save

internal object AobaChanAllDataHandler {
    internal fun reloadAll() {
        WordleData.reload()
    }

    internal fun saveAll() {
        WordleData.save()
    }
}
