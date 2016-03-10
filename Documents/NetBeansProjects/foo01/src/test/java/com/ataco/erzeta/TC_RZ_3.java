/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataco.erzeta;

import static com.ataco.erzeta.TC_RZ_1.sleep;
import com.vaadin.testbench.By;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.NativeButtonElement;
import com.vaadin.testbench.elements.NativeSelectElement;
import com.vaadin.testbench.elements.TextAreaElement;
import com.vaadin.testbench.elements.TextFieldElement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author Jan Konečný
 */
public class TC_RZ_3 extends TestBenchTestCase {

    private final String chromeDriverLocation = "C:\\Users\\konecny\\chromedriver.exe";
    private final String serverURL = "http://localhost:8080/erzeta-ui-0.0.1";

    private static final String testedSourceName = "0TestBench";
    private final int capacity = 3;
    private static final String testedDescription = "Testovací popis zdroje \"TestBench\"";

    @BeforeClass
    public static void databaseSetUp() {
        TC_RZ_2.databaseSetUp();
    }

    @AfterClass
    public static void cleanUp() {
        try {
            TC_RZ_2.statement.executeUpdate("delete from source_authorization WHERE sa_user_id='testovaciuzivatelprotestbench'");
            TC_RZ_2.statement.executeUpdate("delete from sources WHERE so_name='" + testedSourceName + "'");
        } catch (SQLException ex) {
            Logger.getLogger(TC_RZ_3.class.getName()).log(Level.SEVERE, null, ex);
        }
        TC_RZ_2.cleanUp();
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

    /**
     * TC-RZ-3 (hlavní)
     * Test na vytvoření nového zdroje
     */
    @Test
    public void testAddNewSource() {
        NativeButtonElement menuButton = $(NativeButtonElement.class).caption("").first();
        menuButton.click();
        sleep(500);

        NativeButtonElement sourcesButton = $(NativeButtonElement.class).caption("Zdroje").first();
        sourcesButton.click();
        sleep(500);

        clickPlusButton();
        sleep(500);

        TextFieldElement nameLabel = $(TextFieldElement.class).caption("Název").first();
        nameLabel.setValue(testedSourceName);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        TestBenchElement existSince = (TestBenchElement) findElements(By.cssSelector(".v-touchkit-datepicker")).get(0);
        existSince.focus();
        existSince.click();        
        String sequence = String.format("%s" + Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "%s" + Integer.toString(c.get(Calendar.MONTH))
                + Integer.toString(c.get(Calendar.YEAR)), c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "", c.get(Calendar.MONTH) < 10 ? "0" : "");
        Actions actions = new Actions(getDriver());
        actions.sendKeys(sequence);
        actions.build().perform();       

        TestBenchElement existTill = (TestBenchElement) findElements(By.cssSelector(".v-touchkit-datepicker")).get(1);
        existTill.focus();
        existTill.click();
        actions = new Actions(getDriver());
        c.add(Calendar.DATE, 2);
        sequence = String.format("%s" + Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "%s" + Integer.toString(c.get(Calendar.MONTH))
                + Integer.toString(c.get(Calendar.YEAR)), c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "", c.get(Calendar.MONTH) < 10 ? "0" : "");
        actions.sendKeys(sequence);
        actions.build().perform();

        TextFieldElement capacityTextField = $(TextFieldElement.class).caption("Kapacita").first();
        capacityTextField.setValue(Integer.toString(capacity));

        TestBenchElement publicAccessSwitch = (TestBenchElement) findElement(By.cssSelector(".v-touchkit-switch"));
        publicAccessSwitch.click();
        sleep(100);        
        
        TextAreaElement descriptionTextArea = $(TextAreaElement.class).caption("Popis").first();
        descriptionTextArea.setValue(testedDescription);

        TestBenchElement authorizationsTab = (TestBenchElement) findElements(By.cssSelector(".v-tabsheet-tabitem")).get(1);
        authorizationsTab.click();

        clickPlusButton();

        NativeSelectElement usernameSelect = $(NativeSelectElement.class).first();
        usernameSelect.selectByText("testovaciJmeno testovaciPrijmeni");

        NativeButtonElement saveButton = $(NativeButtonElement.class).caption("ULOŽIT").first();
        saveButton.click();
        sleep(500);

        testDataFromNewReservation();
    }

    public void testDataFromNewReservation() {
        List<NativeButtonElement> buttonList = $(NativeButtonElement.class).all();
        for (NativeButtonElement l : buttonList) {
            if (l.getText().contains(testedSourceName)) {
                l.click();
                break;
            }
        }

        TextFieldElement nazev = $(TextFieldElement.class).caption("Název").first();
        Assert.assertEquals(testedSourceName, nazev.getValue());  
        
        TextFieldElement capacityTextField = $(TextFieldElement.class).caption("Kapacita").first();
        Assert.assertEquals(Integer.toString(capacity), capacityTextField.getValue());
        
        Assert.assertNotNull(findElement(By.cssSelector(".v-touchkit-switch-off")));

        TextAreaElement descriptionTextArea = $(TextAreaElement.class).caption("Popis").first();
        Assert.assertEquals(testedDescription, descriptionTextArea.getValue());
        
        TestBenchElement authorizationsTab = (TestBenchElement) findElements(By.cssSelector(".v-tabsheet-tabitem")).get(1);
        authorizationsTab.click();
        sleep(500);
        
        NativeSelectElement usernameSelect = $(NativeSelectElement.class).first();
        Assert.assertEquals("testovaciJmeno testovaciPrijmeni" ,usernameSelect.getValue());
    }

    /** 
     * TC-RZ-2 (vedlejší)
     * Neúspěšné_přidání_nového_zdroje
     */
    @Test
    public void testUnsucesfulAddNewSource() {
        NativeButtonElement menuButton = $(NativeButtonElement.class).caption("").first();
        menuButton.click();
        sleep(500);

        NativeButtonElement sourcesButton = $(NativeButtonElement.class).caption("Zdroje").first();
        sourcesButton.click();
        sleep(500);

        clickPlusButton();
        sleep(500);

        NativeButtonElement saveButton = $(NativeButtonElement.class).caption("ULOŽIT").first();
        saveButton.click();
        sleep(500);

        Assert.assertNotNull(findElement(By.cssSelector(".erzeta-field-error")));
    }

    public void clickPlusButton() {
        Assert.assertNotNull(findElements(By.cssSelector(".v-nativebutton-float-plus-button")));
        TestBenchElement plusButton = (TestBenchElement) findElements(By.cssSelector(".v-nativebutton-float-plus-button")).get(1);
        plusButton.click();
    }
}
