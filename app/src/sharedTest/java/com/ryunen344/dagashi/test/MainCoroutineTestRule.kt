package com.ryunen344.dagashi.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainCoroutineTestRule(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        cleanupTestCoroutines()
    }
}

fun MainCoroutineTestRule.runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
    this.dispatcher.runBlockingTest {
        block()
    }
}

fun MainCoroutineTestRule.CoroutineScope(): CoroutineScope = CoroutineScope(dispatcher)
