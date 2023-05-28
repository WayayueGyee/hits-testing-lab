const baseUrl = "http://localhost:8080"

const pricesInput = document.getElementById("prices-input")
const pricesButton = document.getElementById("prices-button")
const actualResult = document.getElementById("actual-result")
const errorMsgSpan = document.getElementById("error-msg")

const calculateProfit = async () => {
    hideErrorMsg()
    const prices = pricesInput.value.split(",")
    console.log(`Type of prices: '${typeof prices}'. Prices: [${prices}]`)

    const response = await fetch(
        `${baseUrl}/api/alg/max-profit/calculate?` +
            new URLSearchParams({
                prices: prices,
            }),
        {
            method: "GET",
            headers: {
                "Access-Control-Allow-Origin": "*",
            },
        }
    )

    if (response.status == 400) {
        showPricesErrorMsg()
        return
    }

    response.json().then((data) => {
        if (Object.hasOwn(data, "profit")) {
            console.log("Profit: ", data)
            activateResult()
            actualResult.innerHTML = data.profit
        } else {
            console.error("Invalid response from server. Actual data: ", data)
            showPricesErrorMsg("Invalid response from server")
        }
    })
}

const profitResult = document.getElementById("profit-result")

const activateResult = () => {
    profitResult.classList.remove("invisible")
}

const hideErrorMsg = () => {
    errorMsgSpan.classList.add("invisible")
    errorMsgSpan.innerText = ""
}

const showPricesErrorMsg = () => {
    errorMsgSpan.classList.remove("invisible")
    errorMsgSpan.innerText =
        "You must enter from 1 to 10000 comma-separated numbers in the size from 0 to 1000"
}

pricesButton.addEventListener("click", calculateProfit)
