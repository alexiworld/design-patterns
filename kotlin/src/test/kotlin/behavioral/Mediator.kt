package behavioral

import org.junit.jupiter.api.Test

// Mediator
// - Provides a central object used for communicating b/n objects
// - Objects don't talk to each other
// - Reduce dependencies between objects
//
// Adding a new client or more than one to this diagram is unsustainable:
//
//   Client ---  Client
//      |    \/    |
//      |    /\    |
//   Client ---  Client
//
// The solution is:
//
//   Client     Client
//         \   /
//        Mediator
//         /   \
//   Client     Client

class ChatUser(private val mediator: Mediator, private val name: String) {
    fun send(msg: String) {
        println("$name is sending message $msg")
        mediator.sendMessage(msg, this)
    }

    fun receive(msg: String) {
        println("$name received a message $msg")
    }
}

class Mediator {
    private val users = arrayListOf<ChatUser>()

    fun sendMessage(msg: String, user: ChatUser) {
        users.filter { it != user }.forEach {it.receive(msg)}
    }

    fun addUser(user:ChatUser) : Mediator  = apply { users.add(user) }
}

class MediatorTest {
    @Test
    fun testMediator() {
        val mediator = Mediator()
        val alice = ChatUser(mediator, "Alice")
        val bob = ChatUser(mediator, "Bob")
        val carol = ChatUser(mediator, "Carol")

        mediator.addUser(alice).addUser(bob).addUser(carol)

        carol.send("Hi everyone!")
    }
}