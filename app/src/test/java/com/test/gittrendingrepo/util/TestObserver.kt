package com.test.gittrendingrepo.util

import androidx.arch.core.util.Function
import androidx.core.util.Consumer
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.*
import java.util.concurrent.CountDownLatch

class TestObserver<T> private constructor() : Observer<T> {
    private val valueHistory = ArrayList<T>()
    private val onChanged = ArrayList<Consumer<T>>()
    private var valueLatch = CountDownLatch(1)

    override fun onChanged(value: T) {
        valueHistory.add(value)
        valueLatch.countDown()

        for (consumer in onChanged) {
            consumer.accept(value)
        }
    }

    /**
     * Returns a last received value. Fails if no value was received yet.
     *
     * @return a last received value
     */
    private fun value(): T? {
        assertHasValue()
        return valueHistory[valueHistory.size - 1]
    }

    /**
     * Assert that this TestObserver received at least one value.
     *
     * @return this
     */
    private fun assertHasValue(): TestObserver<T> {
        if (valueHistory.isEmpty()) {
            throw fail("Observer never received any value")
        }

        return this
    }

    /**
     * Assert that this TestObserver never received any value.
     *
     * @return this
     */
    fun assertNoValue(): TestObserver<T> {
        if (valueHistory.isNotEmpty()) {
            throw fail("Expected no value, but received: " + value()!!)
        }

        return this
    }

    /**
     * Assert that this TestObserver received the specified number of values.
     *
     * @param expectedSize the expected number of received values
     * @return this
     */
    fun assertHistorySize(expectedSize: Int): TestObserver<T> {
        val size = valueHistory.size
        if (size != expectedSize) {
            throw fail("History size differ; Expected: $expectedSize, Actual: $size")
        }
        return this
    }

    /**
     * Assert that this TestObserver last received value is equal to
     * the given value.
     *
     * @param expected the value to expect being equal to last value, can be null
     * @return this
     */
    fun assertValue(expected: T): TestObserver<T> {
        val value = value()

        if (notEquals(value, expected!!)) {
            throw fail("Expected: " + valueAndClass(expected) + ", Actual: " + valueAndClass(value))
        }

        return this
    }

    /**
     * Asserts that for this TestObserver last received value
     * the provided predicate returns true.
     *
     * @param valuePredicate the predicate that receives the observed value
     * and should return true for the expected value.
     * @return this
     */
    fun assertValue(valuePredicate: Function<T, Boolean>): TestObserver<T> {
        val value = value()

        if (!valuePredicate.apply(value)) {
            throw fail(
                "Value " + valueAndClass(value) + " does not match the predicate "
                        + valuePredicate.toString() + "."
            )
        }

        return this
    }

    private fun fail(message: String): AssertionError {
        return AssertionError(message)
    }

    companion object {

        private fun notEquals(o1: Any?, o2: Any): Boolean {
            return o1 != o2 && (o1 == null || o1 != o2)
        }

        private fun valueAndClass(value: Any?): String {
            return if (value != null) {
                value.toString() + " (class: " + value.javaClass.simpleName + ")"
            } else "null"
        }

        fun <T> test(liveData: LiveData<T>): TestObserver<T> {
            val observer = TestObserver<T>()
            liveData.observeForever(observer)
            return observer
        }
    }
}
