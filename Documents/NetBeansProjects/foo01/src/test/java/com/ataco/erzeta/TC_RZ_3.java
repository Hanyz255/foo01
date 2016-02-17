/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataco.erzeta;

import static com.ataco.erzeta.TC_RZ_1.sleep;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.NativeButtonElement;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Jan Konečný
 */
public class TC_RZ_3 extends TestBenchTestCase {

    private final String chromeDriverLocation = "C:\\Users\\konecny\\chromedriver.exe";
    private final String serverURL = "http://localhost:8080/erzeta-ui-0.0.1";

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
        setDriver(new ChromeDriver());
        getDriver().get(serverURL);
        sleep(100);

        TC_RZ_2 testCase2 = new TC_RZ_2();
        testCase2.setDriver(this.getDriver());
        testCase2.testSuccessfulLogin();
    }

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }
    
    @Test
    public void getSourceMainView(){
        NativeButtonElement menuButton = $(NativeButtonElement.class).caption("").first();
        menuButton.click();
        sleep(500);
        
        NativeButtonElement zdrojeButton = $(NativeButtonElement.class).caption("Zdroje").first();
        zdrojeButton.click();
        sleep(500);
    }
}
