package starter.admin;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import org.json.JSONObject;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class InstantConvert {
    private static final String AUTH_TOKEN = "eyJhbGciOiJkaXIiLCJlbmMiOiJBMjU2Q0JDLUhTNTEyIiwidHlwIjoiSldUIn0..UI1Zg3en_BfBRT3SDfRKcA.AJkyFFG2r2LhXbziOcGZl55Gd0NyZTI1d6Yo0tdSeBuvw62TO004-lWas-DrcWxUa8OkaMYcn2ByQBCYkij--RWv2Fg49DcUmds-GQxQq0B7Met-b7iJv_4CBhnaU3ex3bybJlmDg74vYSmNvWezkmbMGBrL9ErUVZz3eybd94Vb8yc_p0-nz-BV7A7CYsloygFqL--tc2R79Mo-fjobqAztHG9FUO-AJNgwUiFpOKEJ2kiYSZ8ap6WPkD37xpkzroELrkEWmCQlSbMcDZQB_xCZsw8JktBcE4Y_o7gK-mOXV_5RvmUXtFEkuK3hAaJ-sx5bpYw9Fi3kuec7OHNx4mZ4N-UPLmZBolDuJH8kz7ARwUBneHaOydRGO7Bf-fDY-XlCUbjvzis7F12EBzoloOJ5HDWGy02ZBFYwD5iVT-o.gE-bUzzOFx1W7ag9DztN5nMAFRaniHS00o5oHtjL0bE";

    private static final String INSTANT_CONVERT_GET =
            "https://mobee.com/app/instant-convert/__data.json?x-sveltekit-invalidated=11";

    private static final String QUICK_SWAP_CALCULATE =
            "https://service-v2.mobee.io/v2/quick-swap/calculate";

    @Step("I set API endpoint for Instant Convert (GET)")
    public String setInstantConvertGetEndpoint() {
        return INSTANT_CONVERT_GET;
    }

    @Step("I send a GET request to Instant Convert endpoint")
    public void sendGetInstantConvertRequest() {
        SerenityRest.given()
                .header("Bearer", AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .get(setInstantConvertGetEndpoint());
    }

    @Step("I send a POST request with amount below minimum")
    public void sendPostWithAmountBelowMinimum() {
        JSONObject body = new JSONObject();
        body.put("from", "IDR");
        body.put("to", "BTC");
        body.put("amount_from", 15);

        SerenityRest.given()
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .post(QUICK_SWAP_CALCULATE);
    }

    @Step("I send a POST request with amount above maximum")
    public void sendPostWithAmountAboveMaximum() {
        JSONObject body = new JSONObject();
        body.put("from", "IDR");
        body.put("to", "BTC");
        body.put("amount_from", 2000000000);

        SerenityRest.given()
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .post(QUICK_SWAP_CALCULATE);
    }

    @Step("I send a POST request with negative amount")
    public void sendPostWithNegativeAmount() {
        JSONObject body = new JSONObject();
        body.put("from", "IDR");
        body.put("to", "BTC");
        body.put("amount_from", -100);

        SerenityRest.given()
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .post(QUICK_SWAP_CALCULATE);
    }

    @Step("I send a POST request with unsupported currency pair")
    public void sendPostWithUnsupportedCurrencyPair() {
        JSONObject body = new JSONObject();
        body.put("from", "USD");
        body.put("to", "ABC");
        body.put("amount_from", 1000);

        SerenityRest.given()
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .post(QUICK_SWAP_CALCULATE);
    }

    @Step("I send a POST request with zero amount")
    public void sendPostWithZeroAmount() {
        JSONObject body = new JSONObject();
        body.put("from", "IDR");
        body.put("to", "BTC");
        body.put("amount_from", 0);

        SerenityRest.given()
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .post(QUICK_SWAP_CALCULATE);
    }


    @Step("I receive status code {int}")
    public void verifyStatusCode(int code) {
        restAssuredThat(response -> response.statusCode(code));
    }

    @Step("I get valid data for Instant Convert")
    public void validateResponseData() {
        restAssuredThat(response ->
                response.body("nodes[0].data[0].sessionToken", notNullValue()));
        restAssuredThat(response ->
                response.body("nodes[0].data[0].user", notNullValue()));
    }

    @Step("I receive error message {string}")
    public void verifyErrorMessage(String message) {
        String actualError = SerenityRest.lastResponse().jsonPath().getString("error");

        if (message.contains("Maximum amount is")) {
            restAssuredThat(response -> response.body("error", containsString("Maximum amount is")));
            restAssuredThat(response -> response.body("error", containsString("IDR")));
        } else {
            restAssuredThat(response -> response.body("error", containsString(message)));
        }
    }

}
