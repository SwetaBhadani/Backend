package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Hook {

    @Before
    public void InitializeTest(Scenario scenario) {

        System.out.println("Scenario running: " + scenario.getName());
    }


    @After
    public void TearDownTest(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {

            System.out.println(scenario.getName()+" Failed :(");
        } else {
            System.out.println(scenario.getName()+"Scenario Passed :)");
        }

        System.out.println("Scenario complete");
        System.out.println("Closing the browser");

    }
}
