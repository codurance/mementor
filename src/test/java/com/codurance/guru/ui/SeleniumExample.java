package com.codurance.guru.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumExample {

  private static WebDriver driver;

  @BeforeAll
  public static void setUp() {
    System.setProperty("webdriver.chrome.driver", "./chromedriver");
    driver = new ChromeDriver();

  }

  @Test
  public void create_craftperson_yeee() {
    String firstName = "ewan";
    String lastName = "sheldon";

    driver.get("http://localhost:3000");

    addCraftperson(firstName, lastName);
    long count = searchCraftpesronAdded(firstName, lastName);

    assertEquals(0, count);
  }

  private long searchCraftpesronAdded(String firstName, String lastName) {
    List<WebElement> elements = driver.findElements(By.className("craftsperson-name"));
    return elements.stream().filter(e -> e.getText().equals(firstName + " " + lastName)).count();
  }

  private void addCraftperson(String firstName, String lastName) {
    driver.findElement(By.id("adminPopupButton")).click();

    WebElement newCraftsmanFirstName = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div[2]/input[1]"));
    WebElement newCraftsmanLastName = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div[2]/input[2]"));
    newCraftsmanFirstName.sendKeys(firstName);
    newCraftsmanLastName.sendKeys(lastName);

    driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div[2]/div/button")).click();
    driver.findElement(By.id("cancelCraftspersonButton")).click();
  }

  @AfterAll
  public static void cleanUp() {
    if (driver != null) {
      driver.close();
      driver.quit();
    }
  }


}
