package com.hits.testing.alg

import com.hits.testing.alg.exception.OutOfBoundException

class MaxProfit private constructor() {
    companion object {
        private const val MIN_PRICES_LEN = 1
        private const val MAX_PRICES_LEN = 10000
        private const val PRICES_COST_LOWER_BOUNDARY = 0
        private const val PRICES_COST_UPPER_BOUNDARY = 1000

        /**
         * @param prices array of prices for which you need to calculate the maximum profit
         * @return `Result<Int>` indicating if function is succeeded or failed.
         *
         * On failure exception in the `Result` is [OutOfBoundException]
         */
        fun calculate(prices: IntArray): Result<Int> {
            if (prices.size < MIN_PRICES_LEN || prices.size > MAX_PRICES_LEN) return Result.failure(
                OutOfBoundException("Number of prices must be in the range from 1 to 10000")
            )

            if (prices[0] < PRICES_COST_LOWER_BOUNDARY || prices[0] > PRICES_COST_UPPER_BOUNDARY) return Result.failure(
                OutOfBoundException(
                    "Prices must be greater than or equal to 0 and less than or equal to 1000"
                )
            )

            var buy = prices[0]
            var maxDiff = 0

            for (i in 1 until prices.size) {
                if (prices[i] < PRICES_COST_LOWER_BOUNDARY || prices[i] > PRICES_COST_UPPER_BOUNDARY) return Result.failure(
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
