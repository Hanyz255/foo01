/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataco.erzeta;

import static com.ataco.erzeta.TC_RZ_1.sleep;
import com.vaadin.testbench.By;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Jan Konečný
 */
public class TC_RZ_2 extends TestBenchTestCase {
    
    private final String chromeDriverLocation = "C:\\Users\\konecny\\chromedriver.exe";
    private final String serverURL = "http://localhost:8080/erzeta-ui-0.0.1";
    
    private final String testedUserName = "user1";
    private final String testedPassword = "user1";
    
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
        //log.info(System.getProperty("webdriver.chrome.driver"));
        setDriver(new ChromeDriver());
        getDriver().get(serverURL);
        
        sleep(100);
    }
    
    @After
    public void tearDown() {
        getDriver().quit();
    }
    
    @Test
    public void testSuccessfulLogin() {
        TextFieldElement userNameTextField = $(TextFieldElement.class).caption("Uživatelské jméno").first();
        userNameTextField.setValue(testedUserName);
        
        PasswordFieldElement passwordTextField = $(PasswordFieldElement.class).caption("Heslo").first();
        passwordTextField.setValue(testedPassword);
        
        ButtonElement loginButton = $(ButtonElement.class).caption("Přihlásit").first();
        loginButton.click();
        sleep(500);
        
        Assert.assertNotNull(findElements(By.cssSelector(".v-touchkit-navbar")));
    }
    
    @Test
    public void testUnsuccessfulLogin() {
        TextFieldElement userNameTextField = $(TextFieldElement.class).caption("Uživatelské jméno").first();
        userNameTextField.setValue("Chybné uživatelské jméno");
        
        PasswordFieldElement passwordTextField = $(PasswordFieldElement.class).caption("Heslo").first();
        passwordTextField.setValue("Chybné heslo");
        
        ButtonElement loginButton = $(ButtonElement.class).caption("Přihlásit").first();
        loginButton.click();
        
        Assert.assertNotNull(findElement(By.cssSelector(".v-Notification-error")));
    }
    
}
