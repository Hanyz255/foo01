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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Jan Konečný
 */
public class TC_RZ_2 extends TestBenchTestCase {
    
    private final String chromeDriverLocation = "C:\\Users\\konecny\\chromedriver.exe";
    private final String serverURL = "http://localhost:8080/erzeta-ui-0.0.1";
    private static final Logger log = Logger.getLogger(TC_RZ_2.class.getName());
    
    private static final String testedUserName = "testovaciuzivatelprotestbench";
    private static final String testedPassword = "testovaciHeslo";
    
    private static Connection connection;
    public static Statement statement;
    
    @BeforeClass
    public static void databaseSetUp() {
        String url = "jdbc:mysql://localhost/erzeta?useUnicode=yes&amp;characterEncoding=UTF-8";
        String username = "root";
        String password = "root";
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO users VALUES (65535, true, 'testovaci@email.cz', 'testovaciJmeno', 'testovaciPrijmeni', '"+testedUserName+"', true);");
            statement.executeUpdate("INSERT INTO users_password VALUES ('"+testedUserName+"', 'f7NQRHrLuRNUrTae6ycQuF4/8f6vxw9lIfE4p0+KAAE=');");
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void cleanUp() {
        try {
            statement.executeUpdate("delete from users_password WHERE up_username='"+testedUserName+"'");
            statement.executeUpdate("delete from users WHERE us_id=65535");
            
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
        setDriver(new ChromeDriver());
        getDriver().get(serverURL);
        
        sleep(100);
    }
    
    @After
    public void tearDown() {
        getDriver().quit();
    }
    
    /**
     * TC-RZ-2 (hlavní)
     * Test úspěšného přihlášení do systému
     */
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
    
    /**
     * TC-RZ-2 (vedlejší)
     * Neúspěšné_přihlášení_do_systému
     */
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
