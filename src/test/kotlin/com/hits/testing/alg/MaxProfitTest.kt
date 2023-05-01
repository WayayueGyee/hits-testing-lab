package com.hits.testing.alg

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MaxProfitTest {
    @Nested
    inner class PricesBounds {
        @Test
        fun `prices should be less than maximum`() {
            val prices = intArrayOf(1, 2, 3, 4, 1e5.toInt())
            val result = MaxProfit.calculate(prices)
            assert(result.isFailure)
        }

        @Test
        fun `prices should be greater than minimum`() {
            val prices = intArrayOf(0, 1, 2, 3, 4, -1)
            val result = MaxProfit.calculate(prices)
            assert(result.isFailure)
        }
    }

    @Nested
    inner class PricesArrayLength {
        @Test
        fun `prices should not be empty`() {
            val prices = intArrayOf()
            val result = MaxProfit.calculate(prices)
            assert(result.isFailure)
        }

        @Test
        fun `prices length should not exceed maximum`() {
            val prices = IntArray(10000) { it }
            val result = MaxProfit.calculate(prices)
            assert(result.isFailure)
        }
    }
}