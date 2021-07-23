Feature: This feature tests APIs for few scenarios for the given cryptocurrency website

  Background:
    Given The User has the URL and the api key

  Scenario: Retrieve the IDs of given cryptocurrency and convert to a given currency

    When The user hits the request to retrieve the IDs for the cryptocurrencies below
        |Bitcoin |
        |Tether  |
        |Ethereum|
    # "BOB" is currency code for Bolivian Boliviano
    And The user tries to convert the given cryptocurrency to "BOB"
    Then The user gets the converted value of the cryptocurrency to "Bolivian Boliviano"

  Scenario: Retrieve the technical doc website for given crypto currency and verify the data

    When User retrieves the Ethereum (ID 1027) technical documentation website
    Then User verifies the "logo" data is "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png"
    And User verifies the "symbol" data is "ETH"
    And User verifies the "date_added" data is "2015-08-07T00:00:00.000Z"
    And User verifies the "technical_doc" contains "https://github.com/thereum/wiki/wiki/White-Paper"
    And User verifies the "tags" contains "mineable"
