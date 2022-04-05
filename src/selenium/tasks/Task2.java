package selenium.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class Task2 {
    WebDriver driver;

    @Before
    public void openPage() {
        String libWithDriversLocation = System.getProperty("user.dir") + File.separator + "lib" + File.separator;
        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver" + new selenium.ChangeToFileExtension().extension());
        driver = new ChromeDriver();
        driver.get("https://kristinek.github.io/site/tasks/provide_feedback");
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    @Test
    public void initialFeedbackPage() throws Exception {
//         TODO:
//         check that all field are empty and no tick are clicked
        WebElement optMale = driver.findElement(By.xpath("//*[@id=\"fb_form\"]/form/div[4]/input[1]"));
        WebElement optFem = driver.findElement(By.xpath("//*[@id=\"fb_form\"]/form/div[4]/input[2]"));
        WebElement optDk = driver.findElement(By.xpath("//*[@id=\"fb_form\"]/form/div[4]/input[3]"));

        assertFalse(optMale.isSelected());
        assertFalse(optFem.isSelected());
        assertFalse(optDk.isSelected());

//         "Don't know" is selected in "Genre"
        assertFalse(optMale.isSelected());
        assertFalse(optFem.isSelected());
        assertTrue(optDk.isSelected());

//         "Choose your option" in "How do you like us?"
        WebElement howDrop = driver.findElement(By.id("like_us"));
        Select howDropSelect = new Select(howDrop);
        List<WebElement> allSelections;

        howDropSelect.selectByVisibleText("Why me?");

        allSelections = howDropSelect.getAllSelectedOptions();
        assertEquals(1, allSelections.size());
        assertEquals("Why me?", allSelections.get(0).getText());

        assertEquals("You selected the existential crisis", driver.findElement(By.xpath("//*[@id=\"like_us\"]/option[5]")).getText());
//         check that the button send is blue with white letters

        assertEquals("rgba(30, 150, 243, 1)" , driver.findElement(By.className("w3-btn-block w3-blue w3-section")).getCssValue("button"));
        assertEquals("rgba(255, 255, 255, 1)", driver.findElement(By.className("w3-btn-block w3-blue w3-section")).getCssValue("font-color"));
    }

    @Test
    public void emptyFeedbackPage() throws Exception {
//         TODO:
//         click "Send" without entering any data
        driver.findElement(By.className("w3-btn-block w3-blue w3-section")).click();
//         check fields are empty or null
        assertEquals("", driver.findElement(By.className("w3-container")).getText());
//         check button colors
        assertEquals("rgba(76, 175, 80, 1)" , driver.findElement(By.className("w3-btn w3-green w3-xlarge")).getCssValue("button"));
        assertEquals("rgba(255, 255, 255, 1)" , driver.findElement(By.className("w3-btn w3-green w3-xlarge")).getCssValue("font-color"));

        assertEquals("rgba(244, 67, 54, 1)" , driver.findElement(By.className("w3-btn w3-red w3-xlarge")).getCssValue("button"));
        assertEquals("rgba(255, 255, 255, 1)" , driver.findElement(By.className("w3-btn w3-red w3-xlarge")).getCssValue("font-color"));
//         (green with white letter and red with white letters)
    }

    @Test
    public void notEmptyFeedbackPage() throws Exception {
//         TODO:
        WebElement name = driver.findElement(By.id("fb_name"));
        name.sendKeys("Jānītis");

        WebElement optMale = driver.findElement(By.xpath("//*[@id=\"fb_form\"]/form/div[4]/input[1]"));
        WebElement optFem = driver.findElement(By.xpath("//*[@id=\"fb_form\"]/form/div[4]/input[2]"));
        WebElement optDk = driver.findElement(By.xpath("//*[@id=\"fb_form\"]/form/div[4]/input[3]"));

        assertFalse(optMale.isSelected());
        assertTrue(optFem.isSelected());
        assertFalse(optDk.isSelected());

        WebElement howDrop = driver.findElement(By.id("like_us"));
        Select howDropSelect = new Select(howDrop);
        List<WebElement> allSelections;

        howDropSelect.selectByVisibleText("Why me?");

        allSelections = howDropSelect.getAllSelectedOptions();
        assertEquals(1, allSelections.size());
        assertEquals("Why me?", allSelections.get(0).getText());


//         fill the whole form, click "Send"
        driver.findElement(By.className("w3-btn-block w3-blue w3-section")).click();

//         check fields are filled correctly
        assertEquals("All my personal info AND an opinion? ", driver.findElement(By.className("w3-container")).getText());

//         check button colors
        assertEquals("rgba(76, 175, 80, 1)" , driver.findElement(By.className("w3-btn w3-green w3-xlarge")).getCssValue("button"));
        assertEquals("rgba(255, 255, 255, 1)" , driver.findElement(By.className("w3-btn w3-green w3-xlarge")).getCssValue("font-color"));

        assertEquals("rgba(244, 67, 54, 1)" , driver.findElement(By.className("w3-btn w3-red w3-xlarge")).getCssValue("button"));
        assertEquals("rgba(255, 255, 255, 1)" , driver.findElement(By.className("w3-btn w3-red w3-xlarge")).getCssValue("font-color"));
//         (green with white letter and red with white letters)
    }

    @Test
    public void yesOnWithNameFeedbackPage() throws Exception {
//         TODO:
//         enter only name
        WebElement name = driver.findElement(By.id("fb_name"));
        name.sendKeys("Jānītis");
//         click "Send"
        driver.findElement(By.className("w3-btn-block w3-blue w3-section")).click();   // at this point am thinking of just making this a function so
//         click "Yes"                                                                 //i could just write one word but i do not have the energy for that rn
        driver.findElement(By.className("w3-btn w3-green w3-xlarge")).click();
//         check message text: "Thank you, NAME, for your feedback!"
        List<WebElement> list = (List<WebElement>) driver.findElement(By.xpath("//*[@id=\"fb_thx\"]/div"));
//         color of text is white with green on the background
        assertEquals("rgba(244, 67, 54, 1)" , driver.findElement(By.className("w3-panel w3-green")).getCssValue("background"));
        assertEquals("rgba(255, 255, 255, 1)" , driver.findElement(By.className("w3-panel w3-green")).getCssValue("font-color"));
    }

    @Test
    public void yesOnWithoutNameFeedbackPage() throws Exception {
//         TODO:
//         click "Send" (without entering anything
        driver.findElement(By.className("w3-btn-block w3-blue w3-section")).click();
//         click "Yes"
        driver.findElement(By.className("w3-btn w3-green w3-xlarge")).click();
//         check message text: "Thank you for your feedback!"
        List<WebElement> list =(List<WebElement>) driver.findElement(By.xpath("//*[@id=\"fb_thx\"]/div"));
//         color of text is white with green on the background
        assertEquals("rgba(244, 67, 54, 1)" , driver.findElement(By.className("w3-panel w3-green")).getCssValue("background"));
        assertEquals("rgba(255, 255, 255, 1)" , driver.findElement(By.className("w3-panel w3-green")).getCssValue("font-color"));

    }

    @Test
    public void noOnFeedbackPage() throws Exception {
//         TODO:
//         fill the whole form
        WebElement name = driver.findElement(By.id("fb_name"));
        name.sendKeys("Jānītis");

        WebElement optMale = driver.findElement(By.xpath("//*[@id=\"fb_form\"]/form/div[4]/input[1]"));
        WebElement optFem = driver.findElement(By.xpath("//*[@id=\"fb_form\"]/form/div[4]/input[2]"));
        WebElement optDk = driver.findElement(By.xpath("//*[@id=\"fb_form\"]/form/div[4]/input[3]"));

        assertFalse(optMale.isSelected());
        assertTrue(optFem.isSelected());
        assertFalse(optDk.isSelected());

        WebElement howDrop = driver.findElement(By.id("like_us"));
        Select howDropSelect = new Select(howDrop);
        List<WebElement> allSelections;

        howDropSelect.selectByVisibleText("Why me?");

        allSelections = howDropSelect.getAllSelectedOptions();
        assertEquals(1, allSelections.size());
        assertEquals("Why me?", allSelections.get(0).getText());

//         click "Send"
        driver.findElement(By.className("w3-btn-block w3-blue w3-section")).click();
//         click "No"
        driver.findElement(By.className("w3-btn w3-red w3-xlarge")).click();
//         check fields are filled correctly
        assertEquals("All my personal info AND an opinion? ", driver.findElement(By.className("w3-container")).getAttribute(" ")); //tbh iunno
    }
}
