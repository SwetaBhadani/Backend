package stepDefinition;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepDefinition {
    private static String API_KEY;
    private static String BASE_URI;
    private static String mapAPIPath;
    private static String infoAPIPath;
    private static String priceConversionAPIPath;

    private Integer crytoInfoCryptoId;
    private String crytoInfoAPIResponse;

    private Map<String, Integer> cryptoNameToIdMap = new HashMap<>();

    private Map<Integer, Double> cryptoToConvertedCurrencyPriceMap = new HashMap<>();

    @Given("The User has the URL and the api key")
    public void the_user_has_the_url_and_the_api_key() {
        API_KEY = "cf7824dc-62ac-4671-a4bb-af20fe4dbede";
        BASE_URI = "https://pro-api.coinmarketcap.com";
        mapAPIPath = "/v1/cryptocurrency/map";
        infoAPIPath = "/v1/cryptocurrency/info";
        priceConversionAPIPath = "/v2/tools/price-conversion";

        RestAssured.baseURI = BASE_URI;
    }

    @When("The user hits the request to retrieve the IDs for the cryptocurrencies below")
    public void the_user_hits_the_request_to_retrieve_the_i_ds_for_the_cryptocurrencies_below(List<String> dataTable) {

        String mapAPIResponse = RestAssured
                .given()
                .header("X-CMC_PRO_API_KEY", API_KEY)
                .get(mapAPIPath)
                .asString();


        for (String crypto : dataTable) {
            String cryptoJsonPath = String.format("$.data[?(@.name == '%s')].id", crypto);

            List<Integer> bitcoinIdList = JsonPath
                    .parse(mapAPIResponse)
                    .read(cryptoJsonPath);

            cryptoNameToIdMap.put(crypto, bitcoinIdList.get(0));
        }
        System.out.println("The Ids of the given Cryptos: " + cryptoNameToIdMap);
    }

    @When("The user tries to convert the given cryptocurrency to {string}")
    public void the_user_tries_to_convert_the_given_cryptocurrency_to(String covertToThisCurrency) {
        // Write code here that turns the phrase above into concrete actions

        for (Integer cryptoId : cryptoNameToIdMap.values()) {
            String priceConverterResponse = RestAssured.given()
                    .header("X-CMC_PRO_API_KEY", API_KEY)
                    .queryParam("amount", 1)
                    .queryParam("id", cryptoId)
                    .queryParam("convert", covertToThisCurrency)
                    .get(priceConversionAPIPath)
                    .asString();
            // System.out.println(priceConverterResponse);

            String convertingCurrencyJsonPath = String.format("$.data.quote.%s.price", covertToThisCurrency);
            String convertedPrice = JsonPath.parse(priceConverterResponse)
                    .read(convertingCurrencyJsonPath).toString();

            cryptoToConvertedCurrencyPriceMap.put(cryptoId,Double.parseDouble(convertedPrice));
        }

    }

    @Then("The user gets the converted value of the cryptocurrency to {string}")
    public void the_user_gets_the_converted_value_of_the_cryptocurrency_to(String covertToThisCurrency) {

        for (String cryptoName : cryptoNameToIdMap.keySet()){
            int cryptoId=cryptoNameToIdMap.get(cryptoName);
            Double cryptoConvertedPrice=cryptoToConvertedCurrencyPriceMap.get(cryptoId);

            System.out.println("1 "+ cryptoName + " is equal to "+ cryptoConvertedPrice + " " + covertToThisCurrency);
        }
    }


    @When("User retrieves the Ethereum \\(ID {int}) technical documentation website")
    public void user_retrieves_the_ethereum_id_technical_documentation_website(Integer cryptoId) {
        crytoInfoCryptoId = cryptoId;
        crytoInfoAPIResponse = RestAssured
                .given()
                .header("X-CMC_PRO_API_KEY", API_KEY)
                .queryParam("id", cryptoId)
                .get(infoAPIPath)
                .asString();
    }

    @Then("User verifies the {string} data is {string}")
    public void user_verifies_the_data_is(String fieldToVerify, String expectedValue) {

        String jsonPathExpression = String.format("$.data.%d.%s", crytoInfoCryptoId, fieldToVerify);

        String actualValue = JsonPath.parse(crytoInfoAPIResponse)
                                .read(jsonPathExpression).toString();
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Then("User verifies the {string} contains {string}")
    public void user_verifies_the_contains(String fieldToVerify, String expectedValue) {


        switch(fieldToVerify) {
            case "technical_docs":
                // abc;
                String jsonPathExpression1= String.format("$.data.%d.urls.technical_doc",crytoInfoCryptoId);
                String actualValue_techDoc = JsonPath.parse(crytoInfoAPIResponse)
                        .read(jsonPathExpression1).toString();
                Assert.assertTrue(actualValue_techDoc.contains(expectedValue));
                break;
            case "tags":
                String jsonPathExpression2= String.format("$.data.%d.tags",crytoInfoCryptoId);
                String actualValue_tags = JsonPath.parse(crytoInfoAPIResponse)
                        .read(jsonPathExpression2).toString();
                Assert.assertTrue(actualValue_tags.contains(expectedValue));
                break;
            default:
                System.out.println("Verification logic not added!");
        }
    }


}
