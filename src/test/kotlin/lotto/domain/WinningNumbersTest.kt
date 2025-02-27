package lotto.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource

class WinningNumbersTest {
    @Test
    fun `로또 번호 + 보너스 번호 - 로또 번호와 보너스 번호가 중복되는 경우에 대한 예외처리 테스트`() {
        // given
        val lotto = Lotto(listOf(1, 2, 3, 4, 5, 6).map { LottoNumber.from(it) })
        val bonusNumber = LottoNumber.from(1)

        // when
        val exception = assertThrows<IllegalArgumentException> {
            WinningNumbers(lotto, bonusNumber)
        }

        // then
        assertThat(exception.message).isEqualTo("보너스 번호는 당첨 번호와 중복될 수 없습니다.")
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/lotto_numbers.csv"], numLinesToSkip = 1, delimiter = ':')
    fun `로또 번호 + 보너스 번호 - 순위 확인 테스트`(given: String, bonusNumber: Int, expected: String) {
        // given
        val lotto = Lotto(given.split(",").map { LottoNumber.from(it.toInt()) })
        val winningNumbers = Lotto(listOf(1, 2, 3, 4, 5, 6).map { LottoNumber.from(it) })
        val winningNumbersWithBonusNumber = WinningNumbers(winningNumbers, LottoNumber.from(bonusNumber))

        // when
        val actual = winningNumbersWithBonusNumber.findLottoRank(lotto)

        // then
        assertThat(actual).isEqualTo(LottoRank.valueOf(expected))
    }
}
