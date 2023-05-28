package com.hits.testing.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hits.testing.controller.dto.ProfitDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.stream.Stream

@SpringBootTest
@AutoConfigureMockMvc
class MaxProfitControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) {
    private final val baseUrl = "/api/alg/max-profit"

    val calculateEndpoint = "$baseUrl/calculate"

    @Test
    fun `should return 200 OK with expected result`() {
        val prices = intArrayOf(1, 23, 234, 999)
        val expectedResult = ProfitDto(998)

        mockMvc.get("$calculateEndpoint?prices={prices}", prices)
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(expectedResult))
                }
            }
    }

    @ParameterizedTest
    @ArgumentsSource(ArrayIsOutOfBoundsArguments::class)
    fun `BadRequest if prices array length is out of bounds`(prices: IntArray) {
        mockMvc.get("$calculateEndpoint?prices={prices}", prices)
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
                content {
                    contentType("text/plain;charset=UTF-8")
                    string("Number of prices must be in the range from 1 to 10000")
                }
            }
    }

    @ParameterizedTest
    @ArgumentsSource(PricesIsOutOfBoundsArguments::class)
    fun `BadRequest if prices values is out of bounds`(prices: IntArray) {
        mockMvc.get("$calculateEndpoint?prices={prices}", prices)
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
                content {
                    contentType("text/plain;charset=UTF-8")
                    string("Prices must be greater than or equal to 0 and less than or equal to 1000")
                }
            }
    }

    private class ArrayIsOutOfBoundsArguments : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf()),
                Arguments.of(IntArray(10001) { it })
            )
        }
    }

    private class PricesIsOutOfBoundsArguments : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(1, 2, 3, 4, 1e5.toInt())),
                Arguments.of(intArrayOf(0, 1, 2, 3, 4, -1))
            )
        }
    }
}