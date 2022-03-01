@file:Suppress("SpellCheckingInspection")

package io.karlepus.aobachan.command.internal

import net.mamoe.mirai.console.command.CommandExecuteResult
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.MessageChain

// 本文件下代码都来自 https://github.com/project-mirai/chat-command
// 作者为：Him188 https://github.com/Him188  Karlatemp https://github.com/Karlatemp
// 同 mirai https://github.com/Karlatemp 协议 AGPLv3 https://github.com/mamoe/mirai/blob/dev/LICENSE
// 将代码写入自己的插件是为了节省装载 chat-command 的麻烦，emmm，仓库文档会仔细说明并获得作者以及协作者的允许

@OptIn(ExperimentalCommandDescriptors::class, ConsoleExperimentalApi::class)
public suspend fun handleComamnd(sender: CommandSender, message: MessageChain) {
    when (val result = CommandManager.executeCommand(sender, message)) {
        is CommandExecuteResult.PermissionDenied ->
            if ()
    }
}
