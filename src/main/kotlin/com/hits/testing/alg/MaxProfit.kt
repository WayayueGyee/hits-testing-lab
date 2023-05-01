package com.hits.testing.alg

class MaxProfit private constructor() {
    companion object {
        fun calculate(prices: IntArray): Result<Int> {
            var buy = prices[0]
            var maxDiff = 0

            for (i in 1 until prices.size) {
                buy = buy.coerceAtMost(prices[i])
                maxDiff = maxDiff.coerceAtLeast(prices[i] - buy)
            }

            return Result.success(maxDiff)
        }
    }
}
