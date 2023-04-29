package com.hits.testing.alg

class MaxProfit {
    operator fun invoke(prices: IntArray): Int {
        var buy = prices[0]
        var maxDiff = 0
        for (i in 1 until prices.size) {
            buy = buy.coerceAtMost(prices[i])
            maxDiff = maxDiff.coerceAtLeast(prices[i] - buy)
        }
        return maxDiff
    }

}
fun main() {
    val arr = arrayOf<Int>()
    println(arr[0])
}
