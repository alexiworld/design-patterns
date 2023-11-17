package structural

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

// Bridge pattern solves the problem with rising number of implementations/combinations
// between ShapeColor subclasses.
//                    ShapeColor
//                         |
//     +------------+-------------+-------------+
//     |            |             |             |
// RedCircle    BlueCircle    RedSquare    BlueSquare
//
// Imagine if a new color or shape needs to be added, and even if a new attribute needs
// to be added e.g. DimensionsShapeColor, where Dimensions can be 2D, 3D, 4D ...
// The number of classes can exponentially grow.
//
// To solve this kind of problem, Bridge pattern comes to the rescue. The solution would
// look like
//
//             Shape         --------contains------>     Color
//               |                                         |
//     +------------+-------------+                  +-------------+------------+
//     |            |             |                  |             |            |
//  Circle        Square      + Rectangle ...       Red           Blue        +White ...
//

interface Device {
    var volume: Int
    fun getName(): String
}

class Radio : Device {
    override var volume: Int = 0
    override fun getName(): String = "Radio $this"
}

class TV : Device {
    override var volume: Int = 0
    override fun getName(): String = "TV $this"
}

interface Remote {
    fun volumeUp()
    fun volumeDown()
}

class BasicRemote(val device: Device) : Remote {
    override fun volumeUp() {
        device.volume++
        println("${device.getName()} volume up: ${device.volume}")
    }

    override fun volumeDown() {
        device.volume--
        println("${device.getName()} volume down: ${device.volume}")
    }
}

class BridgeTest {

    @Test
    fun testBridge() {
        var tv = TV()
        var radio = Radio()

        val tvRemote = BasicRemote(tv)
        val radioRemote = BasicRemote(radio)

        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeDown()

        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeDown()

        Assertions.assertThat(tvRemote.device.volume).isEqualTo(1)
        Assertions.assertThat(radioRemote.device.volume).isEqualTo(2)
    }

}