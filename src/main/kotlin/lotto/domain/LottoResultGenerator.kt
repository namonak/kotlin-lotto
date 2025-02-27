package lotto.domain

import java.util.EnumMap

data class LottoResultGenerator(private val winningNumbers: WinningNumbers, private val lottos: Lottos) {
    fun getResult(): LottoResult {
        val lottoResult: EnumMap<LottoRank, Int> = initLottoResult()
        lottos.value.forEach { lotto ->
            val lottoRank = winningNumbers.findLottoRank(lotto)
            lottoResult[lottoRank] = lottoResult.getOrDefault(lottoRank, DEFAULT_COUNT) + INCREASE_COUNT
        }
        return LottoResult(lottoResult)
    }

    private fun initLottoResult(): EnumMap<LottoRank, Int> {
        return LottoRank.values()
            .associateWith { DEFAULT_COUNT }
            .toMap(EnumMap(LottoRank::class.java))
    }

    companion object {
        private const val DEFAULT_COUNT = 0
        private const val INCREASE_COUNT = 1
    }
}
