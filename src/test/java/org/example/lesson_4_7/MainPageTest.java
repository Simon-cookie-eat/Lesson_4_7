package org.example.lesson_4_7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTest {
    private WebDriver driver;

    public void clickLinkNumber(List<WebElement> list, int number){
        list.get(number).click();
    }


    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bing.com/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void clickFirstSearchedLink() {
        String seleniumLink = "https://www.selenium.dev/";

        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys("Selenium");
        searchField.submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("h2 > a[href]")));

        List<WebElement> results = driver.findElements(By.cssSelector("h2 > a[href]"));
        clickLinkNumber(results, 0);

        ArrayList tabs = new ArrayList<> (driver.getWindowHandles());
        if (tabs.size() > 1) driver.switchTo().window(tabs.get(1).toString());

        assertEquals(seleniumLink, driver.getCurrentUrl());
    }

}
