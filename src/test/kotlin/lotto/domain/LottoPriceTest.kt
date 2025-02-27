package lotto.domain

import lotto.domain.LottoPrice.Companion.LOTTO_PRICE
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class LottoPriceTest {
    @Test
    fun `로또 구입 - 음수 혹은 1000 이하로 입력에 대한 예외처리 테스트`() {
        // given, when, then
        assertThatThrownBy { LottoPrice(-1) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("로또 구입 금액은 ${LOTTO_PRICE}원 이상이어야 합니다.")
    }

    @Test
    fun `로또 구입 - 숫자 이외의 값 입력에 대한 예외처리 테스트`() {
        // given, when, then
        assertThatThrownBy { LottoPrice("Lotto") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("숫자 이외의 값은 입력할 수 없습니다.")
    }

    @ParameterizedTest
    @CsvSource(value = ["1000:1", "1001:1", "9999:9", "10000:10"], delimiter = ':')
    fun `로또 구입 - 구입 금액에 따른 로또 개수 반환 테스트`(given: Int, expected: Int) {
        // given
        val lottoPrice = LottoPrice(given)

        // when
        val actual = lottoPrice.calculateNumberOfLotto()

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
