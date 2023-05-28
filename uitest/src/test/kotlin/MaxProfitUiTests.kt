import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


class MaxProfitUITests {
    private val url = "http://localhost:5500"

    private lateinit var driver: WebDriver
    private lateinit var wait: WebDriverWait

    @BeforeEach
    fun setUp() {
//        System.setProperty("webdriver.firefox.driver", "/home/vladovello/Programming/chromedriver/chromedriver")
        driver = FirefoxDriver(
            FirefoxOptions()
                .setBinary("/var/lib/flatpak/exports/bin/org.mozilla.firefox")
//                .addArguments("--no-sandbox")
////                .addArguments("--disable-dev-shm-usage")
//                .addArguments("--disable-extensions")
//                .addArguments("--headless")
        )
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
        pricesInput.sendKeys("1,2,3,4,5")
        driver.findElement(By.id("prices-button")).click()

        wait.until(ExpectedConditions.textToBe(By.id("actual-result"), "4"))
        Assertions.assertEquals("Profit: ", driver.findElement(By.id("result-text")).text)
        Assertions.assertEquals("4", driver.findElement(By.id("actual-result")).text)
    }

    @Test
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

    @Test
    fun testLargeInput() {
        driver.get(url)
        wait.until(ExpectedConditions.titleIs("Max Profit"))

        val pricesInput = driver.findElement(By.id("prices-input"))
        pricesInput.sendKeys("1,".repeat(10000))
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
}
