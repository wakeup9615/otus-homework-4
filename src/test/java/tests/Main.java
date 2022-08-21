package tests;

import config.ServerConfig;
import helpers.BaseUITest;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class Main extends BaseUITest {
    public org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    @Test
    @Tag(value = "headless")
    public void headlessTest() {
        driver.get("https://duckduckgo.com/");
        logger.info("Перешли по ссылке");
        driver.findElement(By.cssSelector("#search_form_input_homepage")).sendKeys("ОТУС", Keys.ENTER);
        logger.info("В поисковую строку ввели ОТУС");
        WebElement element = driver.findElement(By.xpath("//*[@id='links']/child::div[1]//*[@data-testid='result-title-a']/span"));
        String expectedText = "Онлайн‑курсы для профессионалов, дистанционное обучение современным ...";
        Assertions.assertEquals(expectedText, element.getText());
        logger.info("Проверили, что в поисковой выдаче первый результат Онлайн‑курсы для профессионалов, дистанционное обучение");
    }

    @Test
    @Tag(value = "fullscreen")
    public void FullScreenTest() {
        driver.get("https://demo.w3layouts.com/demos_new/template_demo/" +
                "03-10-2020/photoflash-liberty-demo_Free/685659620/web/" +
                "index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
        logger.info("Перешли по ссылке");
        driver.findElement(By.xpath("//li[@data-id='id-1']")).click();
        logger.info("Нажали на любую картинку");
        Assertions.assertTrue(driver.findElement(
                By.xpath("//*[@class='pp_pic_holder light_rounded']")).isDisplayed());
        logger.info("Проверили, что картинка открылась в модальном окнеу");
    }

    @Test
    @Tag(value = "maximize")
    public void maximizeTest() {
        driver.get("https://otus.ru");
        logger.info("Перешли по ссылке");
        auth();
        logger.info("Авторизовались");
        logger.info(driver.manage().getCookies());
        logger.info("Вывели Cookie");
    }

    private void auth() {
        driver.findElement(By.cssSelector("button.header2__auth")).click();
        enterToTextArea(driver.findElement(By.xpath("//form[@action='/login/']//input[@name='email']")), cfg.login());
        enterToTextArea(driver.findElement(By.xpath("//form[@action = '/login/']//input[@name = 'password']")), cfg.password());
        driver.findElement(By.xpath("//form[@action = '/login/']//button[@type = 'submit']")).submit();
    }

    private void enterToTextArea(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }
}
