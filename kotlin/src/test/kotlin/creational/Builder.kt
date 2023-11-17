package creational

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Component private constructor(builder: Builder) {
    var param1: String? = null
    var param2: Int? = null
    var param3: Boolean? = null

    class Builder {
        private var param1: String? = null
        private var param2: Int? = null
        private var param3: Boolean? = null

        fun setParam1(param1: String) = apply { this.param1 = param1 }
        fun setParam2(param2: Int) = apply { this.param2 = param2 }
        fun setParam3(param3: Boolean) = apply { this.param3 = param3 }
        fun build(): Component = Component(this)

        fun getParam1() = param1
        fun getParam2() = param2
        fun getParam3() = param3
    }

    init {
        this.param1 = builder.getParam1()
        this.param2 = builder.getParam2()
        this.param3 = builder.getParam3()
    }
}

class ComponentTest {

    @Test
    fun builderTest() {
        val component: Component = Component.Builder()
            .setParam1("Some value")
            .setParam3(true)
            .build()

        Assertions.assertThat(component.param1).isEqualTo("Some value")
        Assertions.assertThat(component.param2).isNull()
        Assertions.assertThat(component.param3).isTrue()
    }

}