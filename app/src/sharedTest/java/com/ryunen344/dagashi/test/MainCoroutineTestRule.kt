package com.ryunen344.dagashi.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MainCoroutineTestRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher(), CoroutineScope by TestScope(dispatcher) {

    val testScheduler: TestCoroutineScheduler
        get() = dispatcher[TestCoroutineScheduler]!!

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }

    fun runBlockingTest(
        context: CoroutineContext = EmptyCoroutineContext,
        dispatchTimeoutMs: Long = DEFAULT_DISPATCH_TIMEOUT_MS,
        block: suspend TestScope.() -> Unit
    ) {
        runTest(
            context = context,
            dispatchTimeoutMs = dispatchTimeoutMs,
            testBody = block
        )
    }

    companion object {
        internal const val DEFAULT_DISPATCH_TIMEOUT_MS = 60_000L
    }
}
