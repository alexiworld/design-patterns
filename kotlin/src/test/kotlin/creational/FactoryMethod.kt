package creational

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

sealed class Country {
    object Canada: Country()

}

object Spain: Country()
class Greece(val someProperty: String): Country()
data class USA(val someProperty: String): Country()
class Poland: Country()

data class Currency(val code: String)

object CurrencyFactory {
    fun currencyForCountry(country: Country): Currency =
        when (country) {
            is Spain -> Currency("EUR")
            is Greece -> Currency("EUR")
            is USA -> Currency("USD")
            is Country.Canada -> Currency("CAD")
            else -> Currency("USD")
        }
}

class FactoryMethodTest {

    @Test
    fun currencyTest() {
        val greekCurrency = CurrencyFactory.currencyForCountry(Greece(""));
        println("Greek currency $greekCurrency")

        val usaCurrency = CurrencyFactory.currencyForCountry(USA(""));
        println("USA currency $usaCurrency")

        Assertions.assertThat(greekCurrency).isEqualTo(Currency("EUR"))
        Assertions.assertThat(usaCurrency).isEqualTo(Currency("USD"))
    }

}
