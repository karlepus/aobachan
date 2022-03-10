@file:Suppress("unused")

package karlepus.aobachan.setting.config

import karlepus.aobachan.AobaChan.reload
import karlepus.aobachan.AobaChan.save

/**
 * 所有的 `配置文件` 都将在此得到处理。
 *
 * @author KarLepus
 */
internal object AobaChanAllConfigHandler {
    /**
     * 重载所有配置文件。
     */
    internal fun reloadAll() {
        AobaChanConfig.reload()
    }

    /**
     * 保存所有配置文件。
     */
    internal fun saveAll() {
        AobaChanConfig.save()
    }
}
