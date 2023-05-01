package com.hits.testing.alg

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MaxProfitTest {
    @Nested
    inner class PricesBoundsTest {
        @Test
        fun `prices should be less than maximum`() {
            val prices = intArrayOf(1, 2, 3, 4, 1e5.toInt())
            val result = MaxProfit.calculate(prices)
            assert(result.isFailure)
        }

        @Test
        fun `prices should be more than minimum`() {
            val prices = intArrayOf(0, 1, 2, 3, 4, -1)
            val result = MaxProfit.calculate(prices)
            assert(result.isFailure)
        }
    }


}