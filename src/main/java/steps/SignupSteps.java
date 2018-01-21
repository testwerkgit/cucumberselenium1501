package steps;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SignupSteps {
    WebDriver driver;
    WebDriverWait wait;

    //Define variables with screen object names.
    public static final String BUTTON_CLASS_SIGNUPBTN = "button[class*='signupbtn']";
    public static final String INPUT_NAME_FIRSTNAME = "input[name=firstname]";
    public static final String INPUT_NAME_LASTNAME = "input[name=lastname]";
    public static final String INPUT_NAME_PHONE = "input[name=phone]";
    public static final String INPUT_NAME_EMAIL = "input[name=email]";
    public static final String INPUT_NAME_PASSWORD = "input[name=password]";
    public static final String INPUT_NAME_CONFIRMPASSWORD = "input[name=confirmpassword]";

    String firstname;
    String lastname;

    public SignupSteps() {
        //Determine OS to select which Driver executable to use.
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/webdrivers/windows/chromedriver.exe");
        } else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/webdrivers/linux/chromedriver");
        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/webdrivers/mac/chromedriver");
        } else {
            fail("Test could not start. No proper webdriver could be determined.");
        }
    }

    @Given("^I open the register page at '(.*)'$")
    public void openPage(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(BUTTON_CLASS_SIGNUPBTN)));
    }

    @When("^I make a new account for user '(.*)'$")
    public void makeNewAccount(String username) {
        driver.findElement(By.cssSelector(INPUT_NAME_FIRSTNAME)).sendKeys("John");
        driver.findElement(By.cssSelector(INPUT_NAME_LASTNAME)).sendKeys("Doe");
        driver.findElement(By.cssSelector(INPUT_NAME_PHONE)).sendKeys("0612345678");
        driver.findElement(By.cssSelector(INPUT_NAME_EMAIL)).sendKeys(username);
        driver.findElement(By.cssSelector(INPUT_NAME_PASSWORD)).sendKeys("henk123");
        driver.findElement(By.cssSelector(INPUT_NAME_CONFIRMPASSWORD)).sendKeys("henk123");
        driver.findElement(By.cssSelector(BUTTON_CLASS_SIGNUPBTN)).click();
    }

    @When("^I make a new account for user with this data$")
    public void makeNewAccountDatatable(DataTable dataTable) {

        String firstname = dataTable.getGherkinRows().get(1).getCells().get(1);
        String lastname = dataTable.getGherkinRows().get(2).getCells().get(1);
        String phone = dataTable.getGherkinRows().get(3).getCells().get(1);
        String email = dataTable.getGherkinRows().get(4).getCells().get(1);
        String password = dataTable.getGherkinRows().get(5).getCells().get(1);
        String confirmpassword = dataTable.getGherkinRows().get(5).getCells().get(1);

        this.firstname = firstname;
        this.lastname = lastname;

        driver.findElement(By.cssSelector(INPUT_NAME_FIRSTNAME)).sendKeys(firstname);
        driver.findElement(By.cssSelector(INPUT_NAME_LASTNAME)).sendKeys(lastname);
        driver.findElement(By.cssSelector(INPUT_NAME_PHONE)).sendKeys(phone);
        driver.findElement(By.cssSelector(INPUT_NAME_EMAIL)).sendKeys(email);
        driver.findElement(By.cssSelector(INPUT_NAME_PASSWORD)).sendKeys(password);
        driver.findElement(By.cssSelector(INPUT_NAME_CONFIRMPASSWORD)).sendKeys(confirmpassword);
        driver.findElement(By.cssSelector(BUTTON_CLASS_SIGNUPBTN)).click();
    }

    @When("^I make the following account for (.*), (.*), (.*), (.*), (.*), (.*)$")
    public void makeAccountsByAbstractScenario(String firstname, String lastname, String phone, String email, String password, String confirmpassword) {
        this.firstname = firstname;
        this.lastname = lastname;

        driver.findElement(By.cssSelector(INPUT_NAME_FIRSTNAME)).sendKeys(firstname);
        driver.findElement(By.cssSelector(INPUT_NAME_LASTNAME)).sendKeys(lastname);
        driver.findElement(By.cssSelector(INPUT_NAME_PHONE)).sendKeys(phone);
        driver.findElement(By.cssSelector(INPUT_NAME_EMAIL)).sendKeys(email);
        driver.findElement(By.cssSelector(INPUT_NAME_PASSWORD)).sendKeys(password);
        driver.findElement(By.cssSelector(INPUT_NAME_CONFIRMPASSWORD)).sendKeys(confirmpassword);
        driver.findElement(By.cssSelector(BUTTON_CLASS_SIGNUPBTN)).click();
    }

    @Then("^the welcome message '(.*)' is displayed$")
    public void displayedMessage(String message) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h3.RTL")));
        assertEquals("Welcome message not displayed.", message, driver.findElement(By.cssSelector("h3.RTL")).getText());
    }

    @Then("^the welcome message is displayed$")
    public void displayedMessageForUser() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h3.RTL")));
        assertEquals("Welcome message not displayed.", "Hi, " + this.firstname + " " + this.lastname, driver.findElement(By.cssSelector("h3.RTL")).getText());
    }

    @Before
    public void startUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}