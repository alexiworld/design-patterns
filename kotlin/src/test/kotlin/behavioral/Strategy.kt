package behavioral

import org.junit.jupiter.api.Test

// Strategy:
// - A class behavioural or algorithm can be changed at run time
// - Object contain algorithm logic
// - Context objects that can handle any algorithm objects
// - Useful when we want to be able to add functionality without
//   changing the program structure
//
//     Client ---calls-----> Executor
//                              |
//                 +------------+-------------+
//                 |            |             |
//             Strategy1    Strategy2     Strategy3

class Printer(private val stringFormatterStrategy: (String)->String) {
    fun printString(text: String) = println(stringFormatterStrategy(text)) // or stringFormatterStrategy.invoke(text)
}

val lowerCaseFormatter = { it: String -> it?.lowercase() }
val upperCaseFormatter = { it: String -> it?.uppercase() }

class StrategyTest {
    @Test
    fun testStrategy() {
        val inputString = "LOREM ipsum DOLOR sit amet"

        val lowerCasePrinter = Printer(lowerCaseFormatter)
        lowerCasePrinter.printString(inputString)

        val upperCasePrinter = Printer(upperCaseFormatter)
        upperCasePrinter.printString(inputString)
    }

}