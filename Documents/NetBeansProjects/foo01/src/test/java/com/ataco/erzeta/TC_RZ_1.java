/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataco.erzeta;

import com.vaadin.testbench.By;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.NativeSelectElement;
import com.vaadin.testbench.elements.SliderElement;
import com.vaadin.testbench.elements.TextAreaElement;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.vaadin.testbench.elements.LabelElement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author Jan Konečný
 */
public class TC_RZ_1 extends TestBenchTestCase {

    private final String chromeDriverLocation = "C:\\Users\\konecny\\chromedriver.exe";
    private final String serverURL = "http://localhost:8080/erzeta-ui-0.0.1";
    private static final Logger log = Logger.getLogger(TC_RZ_1.class.getName());
    
    private static final String testedSourceName = "TestBench";
    private final int quantity = 3;
    private final String testedDescription = "Testovací popis rezervace na zdroji \"TestBench\"";

    private static Connection connection;
    private static Statement statement;
    
    @BeforeClass
    public static void databaseSetUp() {
        TC_RZ_2.databaseSetUp();
        String url = "jdbc:mysql://localhost/erzeta?useUnicode=yes&amp;characterEncoding=UTF-8";
        String username = "root";
        String password = "root";
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO sources VALUES (65535, true, 60, 5, 'zdroj pro TestBench', NOW(), DATE_ADD(NOW(), INTERVAL 10 DAY), '"+testedSourceName+"', false, false, 10, 60);");
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void cleanUp() {
        TC_RZ_2.cleanUp();
        try {
            statement.executeUpdate("delete from reservations WHERE re_source_id=65535");
            statement.executeUpdate("delete from sources WHERE so_id=65535");
            
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }
    
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
    public void testAddNewReservation() {
        Assert.assertNotNull(findElements(By.cssSelector(".v-button-float-plus-button")));
        TestBenchElement plusButton = (TestBenchElement) findElements(By.cssSelector(".v-button-float-plus-button")).get(0);
        plusButton.click();

        NativeSelectElement sourceSelect = $(NativeSelectElement.class).first();
        sourceSelect.selectByText(testedSourceName);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 2);
        int day = c.get(Calendar.DAY_OF_MONTH);

        TestBenchElement datepicker = (TestBenchElement) findElements(By.cssSelector(".v-touchkit-datepicker-datePickerDetailView")).get(1);
        datepicker.focus();
        datepicker.click();
        Actions actions = new Actions(getDriver());
        actions.sendKeys(Integer.toString(day));
        actions.build().perform();

        SliderElement slider1 = $(SliderElement.class).first();
        slider1.click();
        actions = new Actions(getDriver());
        for (int i = 1; i < quantity; i++) {
            actions.sendKeys(Keys.ARROW_RIGHT);
        }
        actions.build().perform();

        TextAreaElement descriptionTextArea = $(TextAreaElement.class).caption("Popis:").first();
        descriptionTextArea.setValue(testedDescription);

        ButtonElement saveButton = $(ButtonElement.class).caption("ULOŽIT").first();
        saveButton.click();

        sleep(500);
        testDataFromNewReservation();
    }

    public void testDataFromNewReservation() {
        List<LabelElement> labelList = $(LabelElement.class).all();
        for (LabelElement l : labelList) {
            if (l.getText().contains(testedDescription)) {
                l.findElement(By.xpath("..")).findElement(By.xpath("..")).click(); //get parrent
                break;
            }
        }

        NativeSelectElement sourceSelect = $(NativeSelectElement.class).first();
        Assert.assertEquals(testedSourceName, sourceSelect.getValue());

        SliderElement slider1 = $(SliderElement.class).first();
        Assert.assertEquals(Integer.toString(quantity), slider1.getValue());

        LabelElement validityLabel = $(LabelElement.class).caption("KE SCHVÁLENÍ").first();
        Assert.assertNotNull(validityLabel);

        TextAreaElement descriptionTextArea = $(TextAreaElement.class).caption("Popis:").first();
        Assert.assertEquals(testedDescription, descriptionTextArea.getValue());
    }

    @Test
    public void testUnsuccessfulAddNewReservation() {
        Assert.assertNotNull(findElements(By.cssSelector(".v-button-float-plus-button")));
        TestBenchElement plusButton = (TestBenchElement) findElements(By.cssSelector(".v-button-float-plus-button")).get(0);
        plusButton.click();
        sleep(500);

        TextAreaElement descriptionTextArea = $(TextAreaElement.class).caption("Popis:").first();
        descriptionTextArea.setValue("");

        ButtonElement saveButton = $(ButtonElement.class).caption("ULOŽIT").first();
        saveButton.click();
        sleep(500);

        Assert.assertNotNull(findElement(By.cssSelector(".erzeta-field-error")));
    }

    /**
     * Wait in milliseconds
     * @param i Milliseconds to sleep
     */
    public static void sleep(int i) {
        try {
            TimeUnit.MILLISECONDS.sleep(i);
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
        }
    }
}
