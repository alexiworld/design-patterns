package behavioral

import org.junit.jupiter.api.Test

// Command:
// A request is wrapped in an object that contains all request info
// The command object is passed to the correct handler

interface Command {
    fun execute()
}

class OrderAddCommand(val id: Long): Command {
    override fun execute() {
        println("Adding order with ID #$id")
    }
}

class OrderPayCommand(val id: Long): Command {
    override fun execute() {
        println("Paying for order with ID #$id")
    }
}

class CommandProcessor {
    private val queue = arrayListOf<Command>() // listOf causes compile error for add and clear

    fun addToQueue(command: Command): CommandProcessor = apply { queue.add(command) }

    fun processCommands() : CommandProcessor = apply {
        queue.forEach { it.execute() }
        queue.clear()

    }

}

class CommandTest {

    @Test
    fun testCommand() {
        CommandProcessor()
            .addToQueue(OrderAddCommand(1L))
            .addToQueue(OrderAddCommand(2L))
            .addToQueue(OrderPayCommand(2L))
            .addToQueue(OrderPayCommand(1L))
            .processCommands()
    }

}