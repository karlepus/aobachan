@file:Suppress("SpellCheckingInspection")

package io.karlepus.aobachan.tool

/**
 * 截图工具。
 *
 * @author KarLepus
 */
public object SnapshotTool {
    /**
     * 通过给定的 [url] 使用 [Chrome Driver](https://chromedriver.chromium.org/) 截取网页图片。
     *
     * 当前驱动版本对应 [Chrome - 谷歌浏览器](https://www.google.cn/chrome/index.html) `"v99.XX"`
     * 版本，使用时注意谷歌浏览器的版本或者将 `"classpath"` 下的 `"chromedriver.exe"` 下载为对应谷歌浏览器的版本即可。
     *
     * @param url 需要截屏的可访问链接。
     */
    public fun webPage(url: String) {}
}
