package com.hits.testing.alg

import com.hits.testing.alg.exception.OutOfBoundException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class MaxProfitTest {
    @Nested
    inner class PricesArrayLength {
        @Test
        fun `prices should not be empty`() {
            val prices = intArrayOf()
            val result = MaxProfit.calculate(prices)
            assert(result.isFailure)
            val e = result.exceptionOrNull()
            assert(e is OutOfBoundException)
            assert(e?.message == "Prices must be in the range from 1 to 10000")
        }

        @Test
        fun `prices length should not exceed maximum`() {
            val prices = IntArray(10001) { it }
            val result = MaxProfit.calculate(prices)
            assert(result.isFailure)
            val e = result.exceptionOrNull()
            assert(e is OutOfBoundException)
            assert(e?.message == "Prices must be in the range from 1 to 10000")
        }
    }

    @Nested
    inner class PricesBounds {
        @Test
        fun `prices should be less than maximum`() {
            val prices = intArrayOf(1, 2, 3, 4, 1e5.toInt())
            val result = MaxProfit.calculate(prices)
            assert(result.isFailure)
            val e = result.exceptionOrNull()
            assert(e is OutOfBoundException)
            assert(e?.message == "Prices must be greater than or equal to 0 and less than or equal to 1000")
        }
        @Test
        fun `prices should be greater than minimum`() {
            val prices = intArrayOf(0, 1, 2, 3, 4, -1)
            val result = MaxProfit.calculate(prices)
            assert(result.isFailure)
            val e = result.exceptionOrNull()
            assert(e is OutOfBoundException)
            assert(e?.message == "Prices must be greater than or equal to 0 and less than or equal to 1000")
        }
    }

    @Nested
    inner class CommonWorkflow {
        @ParameterizedTest
        @ArgumentsSource(CommonArgumentsProvider::class)
        fun `return expected result`(pricesAndResult: Pair<IntArray, Int>) {
            val result = MaxProfit.calculate(pricesAndResult.first)
            assert(result.isSuccess)
            result.getOrNull()?.let {
                assert(it == pricesAndResult.second)
            }
        }

        @ParameterizedTest
        @ArgumentsSource(CrossOverArgumentsProvider::class)
        fun `return 0 if profit cannot be achieved`(prices: IntArray) {
            val result = MaxProfit.calculate(prices)
            assert(result.isSuccess)
            result.getOrNull()?.let {
                assert(it == 0)
            }
        }
    }

    private class CommonArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(Pair(intArrayOf(1), 0)),
                Arguments.of(Pair(intArrayOf(1, 2, 3, 4), 3)),
                Arguments.of(Pair(intArrayOf(1, 23, 234, 999), 998)),
                Arguments.of(Pair(intArrayOf(7, 0, 1, 4, 9, 10, 54, 100), 100)),
            )
        }
    }

    private class CrossOverArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(5, 4, 3, 2, 1)),
                Arguments.of(intArrayOf(1023, 512, 312, 74, 73, 72, 21, 20, 20, 20, 20, 10))
            )
        }
    }
}