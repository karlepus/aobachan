@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package karlepus.aobachan.command.internal

import karlepus.aobachan.util.fuzzySearchMember
import karlepus.aobachan.util.toDecimalPlace
import karlepus.aobachan.util.truncate
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.FriendCommandSender
import net.mamoe.mirai.console.command.GroupAwareCommandSender
import net.mamoe.mirai.console.command.UserCommandSender
import net.mamoe.mirai.console.command.descriptor.AbstractCommandValueArgumentParser
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.nameCardOrNick

/**
 * 用于内部实现自定义命令参数的解析的抽象类，提供一些有用的方法。
 *
 * @author KarLepus
 */
internal abstract class InternalCommandValueArgumentParserExtensions<T : Any> :
    AbstractCommandValueArgumentParser<T>() {
    /**
     * 将字符串转为长整形数据，失败将发送一条特殊的消息。
     */
    private fun String.parseToLongOrFail(): Long = toLongOrNull() ?: illegalArgument("无法解析 $this 为长整数")

    /**
     * 从 [Long] 型的可作为账号的数据，通过 [Bot.getInstanceOrNull] 获取可能的 [Bot] 的实例，失败发送一条特殊的消息。
     */
    internal fun Long.findBotOrFail(): Bot = Bot.getInstanceOrNull(this)
        ?: illegalArgument("无法找到 Bot: $this")

    /**
     * 通过 [Bot.getInstanceOrNull] 将可能的字符串转为账号，获取可能的 [Bot] 的实例，失败则发送一条特殊的消息。
     */
    internal fun String.findBotOrFail(): Bot = Bot.getInstanceOrNull(this.parseToLongOrFail())
        ?: illegalArgument("无法找到 Bot: $this")

    /**
     * 通过 [Bot] 所在的语境，将 [id] 作为账号，搜索可能加入的群，失败则发送一条特熟消息。
     *
     * @param id 群号。
     */
    internal fun Bot.findGroupOrFail(id: Long): Group = getGroup(id) ?: illegalArgument("无法找到 群: $this")

    /**
     * 通过 [Bot] 所在的语境，将字符串型的 [id] 转为长整形作为账号，搜索可能加入的群，失败则发送一条特熟消息。
     *
     * @param id 字符串型群号。
     */
    internal fun Bot.findGroupOrFail(id: String): Group = getGroup(id.parseToLongOrFail())
        ?: illegalArgument("无法找到 群: $this")

    /**
     * 通过 [Bot] 所在的语境，将字符串型的 [id] 作为账号，搜索是否为 [Bot] 的好友，失败则发送一条特殊消息。
     *
     * @param id QQ账号。
     */
    internal fun Bot.findFriendOrFail(id: String): Friend = getFriend(id.parseToLongOrFail())
        ?: illegalArgument("无法找到 好友: $this")

    /**
     * 通过 [Bot] 所在的语境，将字符串型的 [id] 作为账号，搜索是否为 [Bot] 的群员，失败则发送一条特殊消息。
     *
     * @param id QQ账号。
     */
    internal fun Bot.findMemberOrFail(id: String): Friend = getFriend(id.parseToLongOrFail())
        ?: illegalArgument("无法找到 群员: $this")

    /**
     * 在群聊语境下，通过 [idOrCard] 所指代的 `QQ账号` 或 `群名片` 来在群里搜索群成员，失败则发送一条特殊消息。
     *
     * @param idOrCard QQ账号或群名片。
     */
    internal fun Group.findMemberOrFail(idOrCard: String): Member {
        if (idOrCard == "\$") return members.randomOrNull() ?: illegalArgument("当前语境下无法推断随机群员")
        idOrCard.toLongOrNull()?.let { get(it) }?.let { return it }
        members.singleOrNull { it.nameCardOrNick.contains(idOrCard) }?.let { return it }
        members.singleOrNull { it.nameCardOrNick.contains(idOrCard, true) }?.let { return it }
        val conditions: List<Pair<Member, Double>> = this.fuzzySearchMember(idOrCard)
        conditions.singleOrNull()?.let {
            if (it.second == 1.0) return it.first // 匹配第一个 }
        }
        if (conditions.isEmpty()) illegalArgument("无法找到 成员: $idOrCard")
        else {
            var index = 1
            illegalArgument(
                "无法找到 成员: $idOrCard 多个成员满足搜索结果或匹配度不足: \n\n" +
                        conditions.joinToString("\n", limit = 6) {
                            val percentage: String = (it.second * 100).toDecimalPlace(0)
                            "#${index++}($percentage%)${it.first.nameCardOrNick.truncate(10)}" +
                                    "(${it.first.id})" // #1 15.4%
                        }
            )
        }
    }

    /**
     * 推断当前 [CommandSender] 是不是 [Bot] ，失败则发送一条特殊消息。
     */
    internal fun CommandSender.inferBotOrFail(): Bot = (this as? UserCommandSender)?.bot
        ?: Bot.instancesSequence.singleOrNull()
        ?: illegalArgument("当前语境下无法推断目标 Bot, 因为目前有多个 Bot 在线")

    /**
     * 推断当前 [CommandSender] 所在的语境是不是 [Group] ，失败的发送一条特殊消息。
     */
    internal fun CommandSender.inferGroupOrFail(): Group = inferGroup()
        ?: illegalArgument("当前语境下无法推断目标群")

    /**
     * 推断当前 [CommandSender] 所在的语境是 [Group] 。
     */
    internal fun CommandSender.inferGroup(): Group? = (this as? GroupAwareCommandSender)?.group

    /**
     * 推断当前 [CommandSender] 所在的语境是不是 [Friend] ，失败则发送一条特殊消息。
     */
    internal fun CommandSender.inferFriendOrFail(): Friend = (this as? FriendCommandSender)?.user
        ?: illegalArgument("当前语境下无法推断目标好友")
}
