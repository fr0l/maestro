package maestro.cli.model

import maestro.MaestroException
import maestro.cli.report.CommandDebugMetadata
import maestro.cli.report.FlowDebugOutput.Screenshot
import maestro.orchestra.MaestroCommand
import java.util.*
import kotlin.time.Duration

// TODO: Some properties should be implemented as getters, but it's not possible.
//  See https://github.com/Kotlin/kotlinx.serialization/issues/805
data class TestExecutionSummary(
    val passed: Boolean,
    val suites: List<SuiteResult>,
    val passedCount: Int? = null,
    val totalTests: Int? = null,
) {

    data class SuiteResult(
        val passed: Boolean,
        val flows: List<FlowResult>,
        val duration: Duration? = null,
        val deviceName: String? = null,
    )

    data class FlowResult(
        val name: String,
        val fileName: String?,
        val status: FlowStatus,
        val failure: Failure? = null,
        val duration: Duration? = null,
    )

    data class Failure(
        val message: String,
        val commands: IdentityHashMap<MaestroCommand, CommandDebugMetadata>? = null,
        val screenshots: MutableList<Screenshot>? = null,
        var exception: MaestroException? = null
    )
}
