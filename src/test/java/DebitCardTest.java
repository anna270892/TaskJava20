import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class DebitCardTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }
    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    //отправка формы
    @Test
    void submittingTheForm() throws InterruptedException {
        driver.get("http://0.0.0.0:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Кочергина Анна");
        elements.get(1).sendKeys("+79500060445");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    //валидация поля "Фамилия Имя"
    @Test
    void fieldValidationFullName() throws InterruptedException {
        driver.get("http://0.0.0.0:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("111 111");
        elements.get(1).sendKeys("+79500060445");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.xpath("//span[text() ='Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.']")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    //валидация поля "Телефон"
    @Test
    void fieldValidationPhone() throws InterruptedException {
        driver.get("http://0.0.0.0:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Кочергина Анна");
        elements.get(1).sendKeys("Кочергина Анна");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.xpath("//span[text() ='Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.']")).getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    //обязательно к заполнению поле "Фамилия Имя"
    @Test
    void requiredToFillOutFullName() throws InterruptedException {
        driver.get("http://0.0.0.0:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("");
        elements.get(1).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.xpath("//span[text() ='Поле обязательно для заполнения']")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    //обязательно к заполнению поле "Телефон"
    @Test
    void requiredToFillOutPhone() throws InterruptedException {
        driver.get("http://0.0.0.0:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Кочергина Анна");
        elements.get(1).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.xpath("//span[text() ='Поле обязательно для заполнения']")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    //обязательно к заполнению поле "Фамилия Имя"
    @Test
    void asInThePassportFullName() throws InterruptedException {
        driver.get("http://0.0.0.0:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Кочергина");
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.xpath("//span[text() ='Укажите точно как в паспорте']")).getText();
        Assertions.assertEquals("Укажите точно как в паспорте", text.trim());
    }
}
