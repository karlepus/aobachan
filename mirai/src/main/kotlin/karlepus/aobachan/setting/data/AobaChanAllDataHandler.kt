@file:Suppress("unused")

package karlepus.aobachan.setting.data

import karlepus.aobachan.AobaChan.reload
import karlepus.aobachan.AobaChan.save

/**
 * 所有的 `数据文件` 都将在此处得到处理。
 *
 * @author KarLepus
 */
internal object AobaChanAllDataHandler {
    /**
     * 重载所有数据。
     */
    internal fun reloadAll() {
        TeaConData.reload()
    }

    /**
     * 保存所有数据。
     */
    internal fun saveAll() {
        TeaConData.save()
    }
}
