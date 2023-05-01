package com.hits.testing.alg

import com.hits.testing.alg.exception.OutOfBoundException

class MaxProfit private constructor() {
    companion object {
        private const val MIN_PRICES_LEN = 1
        private const val MAX_PRICES_LEN = 10000
        private const val LOWER_BOUNDARY = 0
        private const val UPPER_BOUNDARY = 1000

        fun calculate(prices: IntArray): Result<Int> {
            if (prices.size < MIN_PRICES_LEN || prices.size > MAX_PRICES_LEN) return Result.failure(
                OutOfBoundException("Prices must be in the range from 1 to 10000")
            )

            var buy = prices[0]
            var maxDiff = 0

            for (i in 1 until prices.size) {
                if (prices[i] < LOWER_BOUNDARY || prices[i] > UPPER_BOUNDARY) return Result.failure(
                    OutOfBoundException(
                        "Prices must be greater than or equal to 0 and less than or equal to 1000"
                    )
                )

                buy = buy.coerceAtMost(prices[i])
                maxDiff = maxDiff.coerceAtLeast(prices[i] - buy)
            }

            return Result.success(maxDiff)
        }
    }
}
