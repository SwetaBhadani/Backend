# Backend

This Java maven project uses Rest Assured libraries to automate tests for the given website.
Below are the scenarios.
Scenario 1:
1. Retrieve the ID of bitcoin (BTC), usd tether (USDT), and Ethereum (ETH), using the /cryptocurrency/map call.
2. Once you have retrieved the IDs of these currencies, convert them to Bolivian Boliviano, using the /tools/price-conversion call.
Scenario 2:
1. Retrieve the Ethereum (ID 1027) technical documentation website from the cryptocurrency/info call.
2. Verifies the below data   
   "logo" data is "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png"
   "symbol" data is "ETH"
   "date_added" data is "2015-08-07T00:00:00.000Z"
   "technical_doc" contains "https://github.com/thereum/wiki/wiki/White-Paper"
   "tags" contains "mineable"
   
To run the tests, follow the steps below:
Through IDE:

    1. Import the project to Eclipse/IntelliJ
    2. Right click on src/test/java/runner/TestRunner or src/test/java/features/Feature.feature and select 'Run TestRunner' or 'Run Feature.feature' correspondingly.
Through Command line:

    1. Go to project path
    2. Run " mvn clean test "