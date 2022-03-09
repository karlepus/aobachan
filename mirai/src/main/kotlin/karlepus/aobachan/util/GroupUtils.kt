package karlepus.aobachan.util

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.NormalMember

/**
 * 通过 [Group] 相关信息模糊搜索群成员。
 *
 * @return 所有可能的结果集（一个 [Pair] 键值对，以 [NormalMember] 和 [Double] 所代表的的 `群成员` 和 `匹配率`
 * 作为 `key/value` ）。
 */
public fun Group.fuzzySearchMember(
    /**
     * 名片对象。
     */
    nameCardTarget: String,
    /**
     * 最小概率（参与判断，用于提示可能的解）。
     */
    minRate: Double = 0.2,
    /**
     * 匹配概率（最终选择的最少匹配率，减少歧义）。
     */
    matchRate: Double = 0.6,
    /**
     * 消歧义概率，如果有多个值超过 [matchRate] ，并相互差距小于等于 [disambiguationRate] ，则认为有较大歧义风险，
     * 返回可能的解的列表。
     */
    disambiguationRate: Double = 0.1
): List<Pair<Member, Double>> {
    val candidates: List<Pair<NormalMember, Double>> = (this.members + botAsMember)
        .asSequence()
        .associateWith { it.nameCard.fuzzyMatchWith(nameCardTarget) }
        .filter { it.value >= minRate }
        .toList().sortedByDescending { it.second }
    val bestMatches: List<Pair<NormalMember, Double>> = candidates.filter { it.second >= matchRate }
    return when {
        bestMatches.isEmpty() -> candidates
        bestMatches.size == 1 -> listOf(bestMatches.single().first to 1.0)
        else -> if (bestMatches.first().second - bestMatches.last().second <= disambiguationRate) candidates // 消歧义
        else listOf(bestMatches.first().first to 1.0)
    }
}
