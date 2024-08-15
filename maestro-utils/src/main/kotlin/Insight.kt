package maestro.utils

import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentLinkedQueue

object Insights {
    private val logger = LoggerFactory.getLogger(Insights::class.java)

    private val listeners = ConcurrentLinkedQueue<(Insight) -> Unit>()

    fun report(insight: Insight?) {
        if (insight != null) {
            listeners.forEach { it.invoke(insight) }
        } else {
            logger.error("report: The insight object is NULL. The call stack is this:\n" + Thread.currentThread().stackTrace.joinToString("\n"))
        }
    }

    fun onInsightsUpdated(callback: ((Insight) -> Unit)?) {
        if (callback != null) {
            listeners.add(callback)
        } else {
            logger.error("onInsightsUpdated: The insight object is NULL. The call stack is this:\n" + Thread.currentThread().stackTrace.joinToString("\n"))
        }
    }

    fun unregisterListener(callback: ((Insight) -> Unit)?) {
        if (callback != null) {
            listeners.remove(callback)
        } else {
            logger.error("unregisterListener: The insight object is NULL. The call stack is this:\n" + Thread.currentThread().stackTrace.joinToString("\n"))
        }
    }
}

data class Insight(
    val message: String,
    val level: Level
) {
    enum class Level {
        WARNING,
        INFO,
        NONE
    }
}
