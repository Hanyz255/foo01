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
import com.vaadin.testbench.elements.HorizontalLayoutElement;
import com.vaadin.testbench.elements.NativeSelectElement;
import com.vaadin.testbench.elements.SliderElement;
import com.vaadin.testbench.elements.TextAreaElement;
import com.vaadin.testbench.elements.VerticalLayoutElement;
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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;
import java.util.List;
import org.junit.BeforeClass;

/**
 *
 * @author Jan Konečný
 */
@FixMethodOrder(MethodSorters.JVM) //Spousteni testovacich metod v abecednim poradi
public class TestCaseErzeta1 extends TestBenchTestCase {

    private static final Logger log = Logger.getLogger(TestCaseErzeta1.class.getName());
    private final String testedSourceName = "TestBench";
    private final int quantity = 3;
    private final String testedDescription = "Testovací popis rezervace na zdroji \"TestBench\"";
    
    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\konecny\\chromedriver.exe");
        //log.info(System.getProperty("webdriver.chrome.driver"));
        setDriver(new ChromeDriver());
        getDriver().get("http://localhost:8080/erzeta-ui-0.0.1");
        
        waitInMilliseconds(100);
        TextFieldElement userNameTextField = $(TextFieldElement.class).caption("Uživatelské jméno").first();
        userNameTextField.setValue("user1");
        
        PasswordFieldElement passwordTextField = $(PasswordFieldElement.class).caption("Heslo").first();
        passwordTextField.setValue("user1");
        
        ButtonElement loginButton = $(ButtonElement.class).caption("Přihlásit").first();
        loginButton.click();
        waitInMilliseconds(1000);
    }

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }

    @Test
    public void testAddNewReservation() {
        Assert.assertTrue($(VerticalLayoutElement.class).$$(ButtonElement.class).exists());
        ButtonElement plusButton = $(VerticalLayoutElement.class).$$(ButtonElement.class).first();
        plusButton.click();

        NativeSelectElement sourceSelect = $(NativeSelectElement.class).first();
        sourceSelect.selectByText(testedSourceName); //TODO: pridat dynamicky vyber zdroje z databaze nebo mock

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 7);
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

        waitInMilliseconds(500);
        testDataFromNewReservation();

        /*
         findElement(By.cssSelector(".v-touchkit-navbutton-back")).click();
         */
    }

    public void testDataFromNewReservation() {
        List<LabelElement> labelList = $(HorizontalLayoutElement.class).$$(LabelElement.class).all();
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
        Assert.assertTrue($(VerticalLayoutElement.class).$$(ButtonElement.class).exists());
        ButtonElement plusButton = $(VerticalLayoutElement.class).$$(ButtonElement.class).first();
        plusButton.click();
        waitInMilliseconds(500);

        TextAreaElement descriptionTextArea = $(TextAreaElement.class).caption("Popis:").first();
        descriptionTextArea.setValue("");

        ButtonElement saveButton = $(ButtonElement.class).caption("ULOŽIT").first();
        saveButton.click();
        waitInMilliseconds(500);

        Assert.assertNotNull(findElement(By.cssSelector(".v-Notification-error")));
    }

    @Test
    public void testDeletingReservation() {
        List<LabelElement> labelList = $(HorizontalLayoutElement.class).$$(LabelElement.class).all();
        for (LabelElement l : labelList) {
            if (l.getText().contains(testedDescription)) {
                l.findElement(By.xpath("..")).findElement(By.xpath("..")).click(); //get parrent
                break;
            }
        } 

        waitInMilliseconds(500);
        ButtonElement deleteButton = $(ButtonElement.class).caption("SMAZAT").first();
        deleteButton.click();
        waitInMilliseconds(500);

        TestBenchElement yesButton = (TestBenchElement) findElements(By.cssSelector(".v-button")).get(4);
        yesButton.click();
    }

    public void waitInMilliseconds(int i) {
        try {
            TimeUnit.MILLISECONDS.sleep(i);
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
        }
    }
}
