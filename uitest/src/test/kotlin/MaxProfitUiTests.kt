import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.stream.Stream


class MaxProfitUITests {
    private val url = "http://localhost:5500/frontend"

    private lateinit var driver: WebDriver
    private lateinit var wait: WebDriverWait

    @BeforeEach
    fun setUp() {
        driver = ChromeDriver()
        wait = WebDriverWait(driver, Duration.ofSeconds(10))
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun testCalculateProfit() {
        driver.get(url)
        wait.until(ExpectedConditions.titleIs("Max Profit"))

        val pricesInput = driver.findElement(By.id("prices-input"))
        pricesInput.sendKeys("1,2,3,1,5")
        driver.findElement(By.id("prices-button")).click()

        wait.until(ExpectedConditions.textToBe(By.id("actual-result"), "4"))
        Assertions.assertEquals("Profit:", driver.findElement(By.id("result-text")).text)
        Assertions.assertEquals("4", driver.findElement(By.id("actual-result")).text)
    }


    @Nested
    inner class InvalidInputs {
        @ParameterizedTest
        @ArgumentsSource(InvalidDataArgumentsProvider::class)
        fun testInvalidInput() {
            driver.get(url)
            wait.until(ExpectedConditions.titleIs("Max Profit"))

            val pricesInput = driver.findElement(By.id("prices-input"))
            pricesInput.sendKeys("1,abc,3,4,5")
            driver.findElement(By.id("prices-button")).click()

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-msg")))
            Assertions.assertEquals(
                "You must enter from 1 to 10000 comma-separated numbers in the size from 0 to 1000",
                driver.findElement(By.id("error-msg")).text
            )
        }
    }

    @Test
    fun testEmptyInput() {
        driver.get(url)
        wait.until(ExpectedConditions.titleIs("Max Profit"))

        driver.findElement(By.id("prices-button")).click()

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-msg")))
        Assertions.assertEquals(
            "You must enter from 1 to 10000 comma-separated numbers in the size from 0 to 1000",
            driver.findElement(By.id("error-msg")).text
        )
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setupClass() {
            WebDriverManager.firefoxdriver().setup()
        }
    }

    private class InvalidDataArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of("1,abc,3,4,5"),
                Arguments.of("1,,3,4,5"),
                Arguments.of("1 3,4,5"),
                Arguments.of("1, 3,4,5,")
            )
        }
    }
}
