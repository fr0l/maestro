package maestro.utils

import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentLinkedQueue

object CliInsights: Insights {
    private val logger = LoggerFactory.getLogger(Insights::class.java)

    private val listeners = ConcurrentLinkedQueue<(Insight) -> Unit>()

    override fun report(insight: Insight) {
        if (insight != null) {
            listeners.forEach { it.invoke(insight) }
        } else {
            logger.error("report: The insight object is NULL. The call stack is this:\n" + Thread.currentThread().stackTrace.joinToString("\n"))
        }
    }

    override fun onInsightsUpdated(callback: (Insight) -> Unit) {
        if (callback != null) {
            listeners.add(callback)
        } else {
            logger.error("onInsightsUpdated: The insight object is NULL. The call stack is this:\n" + Thread.currentThread().stackTrace.joinToString("\n"))
        }
    }

    override fun unregisterListener(callback: (Insight) -> Unit) {
        if (callback != null) {
            listeners.remove(callback)
        } else {
            logger.error("unregisterListener: The insight object is NULL. The call stack is this:\n" + Thread.currentThread().stackTrace.joinToString("\n"))
        }
    }
}
