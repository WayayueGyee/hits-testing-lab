package com.hits.testing.alg

import com.hits.testing.alg.exception.OutOfBoundException

class MaxProfit private constructor() {
    companion object {
        private const val LOWER_BOUNDARY = 0
        private const val UPPER_BOUNDARY = 1000

        fun calculate(prices: IntArray): Result<Int> {
            var buy = prices[0]
            var maxDiff = 0

            for (i in 1 until prices.size) {
                if (prices[i] < LOWER_BOUNDARY || prices[i] > UPPER_BOUNDARY) return Result.failure(OutOfBoundException())

                buy = buy.coerceAtMost(prices[i])
                maxDiff = maxDiff.coerceAtLeast(prices[i] - buy)
            }

            return Result.success(maxDiff)
        }
    }
}
