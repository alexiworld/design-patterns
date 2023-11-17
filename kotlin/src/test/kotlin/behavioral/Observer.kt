package behavioral

import org.junit.jupiter.api.Test
import java.io.File

interface EventListener {
    fun update(eventType: String?, file: File?);
}

class EventManager(vararg operations: String) {
    var listeners = hashMapOf<String, ArrayList<EventListener>>()

    init {
        for (operation in operations) {
            listeners[operation] = ArrayList<EventListener>() // or put(key, val)
        }
    }

    fun subscribe(eventType: String, eventListener: EventListener) {
        listeners[eventType]!!.add(eventListener) // !!. non-null asserted call, preferred than ?. in this case
    }

    fun unsubscribe(eventType: String, eventListener: EventListener) {
        listeners[eventType]!!.remove(eventListener)
    }

    fun notify(eventType: String?, file: File?) {
        var eventTypelisteners = listeners.get(eventType) // check if different from =listeners[eventType]
        eventTypelisteners?.let {
            for (eventTypelistener in it) eventTypelistener.update(eventType, file)
        }
    }
}

class EventGenerator {
    var eventManager = EventManager("open", "save")

    var file: File? = null

    fun openFile(filePath: String) {
        file = File(filePath)
        eventManager.notify("open", file)
    }

    fun saveFile() {
        file?.let { eventManager.notify("save", file) }
    }

}

class EmailNotificationListener(private val email: String): EventListener {
    override fun update(eventType: String?, file: File?) {
        println("Email to $email: Someone has performed $eventType operation on file ${file?.name}")
    }
}

class LogOpenListener(private val filename: String): EventListener {
    override fun update(eventType: String?, file: File?) {
        println("Save to log $filename: Someone has performed $eventType operation on file ${file?.name}")
    }
}

class ObserverTest {

    @Test
    fun testObserver() {
        val eventGenerator = EventGenerator() // e.g. Editor()

        val logOpenListener = LogOpenListener("events.log")
        val emailNotificationListener = EmailNotificationListener("test@test.org")

        eventGenerator.eventManager.subscribe("open", logOpenListener)
        eventGenerator.eventManager.subscribe("open", emailNotificationListener)
        eventGenerator.eventManager.subscribe("save", emailNotificationListener)

        eventGenerator.openFile("test.csv")
        eventGenerator.saveFile()

    }

}