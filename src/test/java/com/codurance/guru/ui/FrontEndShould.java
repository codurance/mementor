package com.codurance.guru.ui;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FrontEndShould {
    @Test
    public void add_a_new_craftsperson() throws InterruptedException {
        // Optional. If not specified, WebDriver searches the PATH for chromedriver.

        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:3000");
        WebElement adminButton = driver.findElement(By.xpath("//*[contains(text(),'Admin')]"));
        adminButton.click();

        WebElement firstNameInput = driver.findElement(By.xpath("//input[@placeholder=\"First Name...\"]"));
        firstNameInput.sendKeys("Jerry");

        WebElement lastNameInput = driver.findElement(By.xpath("//input[@placeholder=\"Last Name...\"]"));
        lastNameInput.sendKeys("Lewis");

        WebElement addButton = driver.findElement(By.xpath("//*[contains(text(),'Add')]"));
        addButton.click();

        WebElement closeButton = driver.findElement(By.className("close"));
        closeButton.click();

        WebElement person = driver.findElement(By.name("JerryLewis"));
        assertTrue(person.isDisplayed());

        Thread.sleep(5000);
        driver.quit();
    }
}
