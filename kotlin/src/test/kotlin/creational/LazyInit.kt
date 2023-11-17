package creational

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AlertBox {

    var message: String? = null

    fun show() {
        println("AlertBox $this: $message")
    }

}

class Window {

    val box by lazy { AlertBox() }

    fun showMessage(message: String) {
        box.message = message
        box.show()
    }

}

class Window2 {
    lateinit var box: AlertBox

    fun showMessage(message: String) {
        box = AlertBox()
        box.message = message
        box.show()
    }
}

class WindowTest {

    @Test
    fun windowTest() {
        var window = Window()
        window.showMessage("Hello Window!")
        Assertions.assertThat(window.box).isNotNull()

        var window2 = Window2()
        //println(window2.box) // with this line you should be getting kotlin.UninitializedPropertyAccessException: lateinit property box has not been initialized
        window2.showMessage("Hello Window2!")
        Assertions.assertThat(window2.box).isNotNull()
    }

}