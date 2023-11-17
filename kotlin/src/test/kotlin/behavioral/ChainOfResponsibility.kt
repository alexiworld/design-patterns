package behavioral

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

// Chain of Responsibility:
// - Define a chain of handlers to process the request
// - Each handler contains a reference to the next handler
// - Each handler decides to process the request AND/OR pass it on
// - Request can be of different types
//
//                               Result
//                                  ^
//                                  |
//   Request ----> Handler ----> Handler ----> Handler
//                    |                           ^
//                    |                           |
//                    +---------------------------+
//
// Optionally (as shown on above diagram):
// - Each handler can stop the chain and return the result
// - Each handler may be able to pass the processing to any other handler

interface HandlerChain {
    fun addHeader(inputHeader: String): String
}

class AuthenticationHandler(val token: String?, var nextHandler: HandlerChain? = null) : HandlerChain { // val, not var nextHandler to allow changes
    override fun addHeader(inputHeader: String): String =
        "$inputHeader\nAuthorization: $token"
            .let { nextHandler?.addHeader(it) ?: it }  // if nextHandler is undefined than ?: it will be returned
}

class ContentTypeHandler(val contentType: String?, var nextHandler: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String): String =
        "$inputHeader\nContent-Type: $contentType"
            .let { nextHandler?.addHeader(it) ?: it }
}

class BodyPayloadHandler(val body: String?, var nextHandler: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String): String =
        "$inputHeader\n$body"
            .let { nextHandler?.addHeader(it) ?: it }
}


class ChainOfResponsibilityTest {
    @Test
    fun testChainOfResponsibility() {
        val authenticationHandler = AuthenticationHandler("123456")
        val contentTypeHandler = ContentTypeHandler("application/json")
        val bodyPayloadHandler = BodyPayloadHandler("Body: {\"username\": \"alexiworld\"}")

        authenticationHandler.nextHandler = contentTypeHandler
        contentTypeHandler.nextHandler = bodyPayloadHandler

        val messageAuthentication = authenticationHandler.addHeader("Headers with authentication")
        println(messageAuthentication)

        println("--------------------------")

        val messageWithoutAuthentication = contentTypeHandler.addHeader("Headers without authentication")
        println(messageWithoutAuthentication)

        Assertions.assertThat(messageAuthentication).isEqualTo(
            """
                Headers with authentication
                Authorization: 123456
                Content-Type: application/json
                Body: {"username": "alexiworld"}
            """.trimIndent()
        )

        Assertions.assertThat(messageWithoutAuthentication).isEqualTo(
            """
                Headers without authentication
                Content-Type: application/json
                Body: {"username": "alexiworld"}
            """.trimIndent()
        )
    }
}