package com.hits.testing.controller

import com.hits.testing.alg.MaxProfit
import com.hits.testing.alg.exception.OutOfBoundException
import com.hits.testing.controller.dto.ProfitDto
import com.hits.testing.util.LoggerDelegate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/alg/max-profit")
class MaxProfitController {
    private val logger by LoggerDelegate()

    @CrossOrigin(origins = ["*"], exposedHeaders = ["*"])
    @GetMapping("calculate")
    fun calculateProfit(@RequestParam prices: IntArray): ResponseEntity<*> {
        val profitResult = MaxProfit.calculate(prices)

        return profitResult.fold(
            { value -> ResponseEntity.ok(ProfitDto(value)) },
            { e ->
                return@fold when (e) {
                    is OutOfBoundException -> {
                        logger.info(e.message)
                        ResponseEntity.badRequest().body(e.message)
                    }

                    else -> {
                        logger.severe(e.stackTraceToString())
                        ResponseEntity.internalServerError().body("Something went wrong on the server")
                    }
                }
            }
        )
    }
}
