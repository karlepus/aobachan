package io.karlepus.aobachan.command.internal

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.FriendCommandSender
import net.mamoe.mirai.console.command.GroupAwareCommandSender
import net.mamoe.mirai.console.command.UserCommandSender
import net.mamoe.mirai.console.command.descriptor.AbstractCommandValueArgumentParser
import net.mamoe.mirai.console.command.descriptor.toDecimalPlace
import net.mamoe.mirai.console.command.descriptor.truncate
import net.mamoe.mirai.console.internal.command.fuzzySearchMember
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.nameCardOrNick

internal abstract class InternalCommandValueArgumentParserExtensions<T : Any> :
    AbstractCommandValueArgumentParser<T>() {
    private fun String.parseToLongOrFail(): Long = toLongOrNull() ?: illegalArgument("无法解析 $this 为整数")

    protected fun Long.findBotOrFail(): Bot = Bot.getInstanceOrNull(this) ?: illegalArgument("无法找到 Bot: $this")

    protected fun String.findBotOrFail(): Bot =
        Bot.getInstanceOrNull(this.parseToLongOrFail()) ?: illegalArgument("无法找到 Bot: $this")

    protected fun Bot.findGroupOrFail(id: Long): Group = getGroup(id) ?: illegalArgument("无法找到群: $this")

    protected fun Bot.findGroupOrFail(id: String): Group =
        getGroup(id.parseToLongOrFail()) ?: illegalArgument("无法找到群: $this")

    protected fun Bot.findFriendOrFail(id: String): Friend =
        getFriend(id.parseToLongOrFail()) ?: illegalArgument("无法找到好友: $this")

    protected fun Bot.findMemberOrFail(id: String): Friend =
        getFriend(id.parseToLongOrFail()) ?: illegalArgument("无法找到群员: $this")

    protected fun Group.findMemberOrFail(idOrCard: String): Member {
        if (idOrCard == "\$") return members.randomOrNull() ?: illegalArgument("当前语境下无法推断随机群员")
        idOrCard.toLongOrNull()?.let { get(it) }?.let { return it }
        this.members.singleOrNull { it.nameCardOrNick.contains(idOrCard) }?.let { return it }
        this.members.singleOrNull { it.nameCardOrNick.contains(idOrCard, ignoreCase = true) }?.let { return it }

        val candidates = this.fuzzySearchMember(idOrCard)
        candidates.singleOrNull()?.let {
            if (it.second == 1.0) return it.first // single match
        }
        if (candidates.isEmpty()) {
            illegalArgument("无法找到成员 $idOrCard")
        } else {
            var index = 1
            illegalArgument(
                "无法找到成员 $idOrCard。 多个成员满足搜索结果或匹配度不足: \n\n" +
                        candidates.joinToString("\n", limit = 6) {
                            val percentage = (it.second * 100).toDecimalPlace(0)
                            "#${index++}(${percentage}%)${it.first.nameCardOrNick.truncate(10)}(${it.first.id})" // #1 15.4%
                        }
            )
        }
    }

    protected fun CommandSender.inferBotOrFail(): Bot =
        (this as? UserCommandSender)?.bot
            ?: Bot.instancesSequence.singleOrNull()
            ?: illegalArgument("当前语境下无法推断目标 Bot, 因为目前有多个 Bot 在线.")

    protected fun CommandSender.inferGroupOrFail(): Group =
        inferGroup() ?: illegalArgument("当前语境下无法推断目标群")

    protected fun CommandSender.inferGroup(): Group? = (this as? GroupAwareCommandSender)?.group

    protected fun CommandSender.inferFriendOrFail(): Friend =
        (this as? FriendCommandSender)?.user ?: illegalArgument("当前语境下无法推断目标好友")
}
