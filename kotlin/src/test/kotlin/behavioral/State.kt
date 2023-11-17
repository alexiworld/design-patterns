package behavioral

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

// State:
//
// - An object changes its behaviour based on an internal state
// - At any moment, there is a finite number of states a program can be in
// - State can be encapsulated in an object
//
//     +-----  Published
//     |           ^
//     |           |
//     |       Moderation  --+
//     |           ^         |
//     |           |         |
//     +-------> Draft <-----+

sealed class AuthorizationState

object Unauthorized: AuthorizationState()

class Authorized(val username: String) : AuthorizationState()

class AuthorizationPresenter {
    private var state: AuthorizationState = Unauthorized

    val isAuthorized: Boolean
        get() = when(state) {
            is Authorized -> true
            is Unauthorized -> false
        }

    val username: String
    get() {
        return when (val state: AuthorizationState = this.state) {
            is Authorized -> state.username
            is Unauthorized -> "Unknown"
        }
    }

    fun loginUser(username: String) {
        state = Authorized(username)
    }

    fun logoutUser() {
        state = Unauthorized
    }

    override fun toString(): String = "User $username is logged in: $isAuthorized"

}

class StateTest {
    @Test
    fun testState() {
        val authorizationPresenter = AuthorizationPresenter()
        authorizationPresenter.loginUser("admin")
        println(authorizationPresenter)
        Assertions.assertThat(authorizationPresenter.isAuthorized).isEqualTo(true)
        Assertions.assertThat(authorizationPresenter.username).isEqualTo("admin")

        authorizationPresenter.logoutUser()
        println(authorizationPresenter)
        Assertions.assertThat(authorizationPresenter.isAuthorized).isEqualTo(false)
        Assertions.assertThat(authorizationPresenter.username).isEqualTo("Unknown")
    }

}