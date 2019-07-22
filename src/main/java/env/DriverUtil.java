package env;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class DriverUtil {

    private static final String PATH_TO_CHROME_DRIVER = "C:/dev/apps/chromedriver.exe"; //TODO move to properties
    private static long DEFAULT_WAIT = 20; //TODO move to properties

    private static WebDriver driver = null;

    public static WebDriver getDefaultDriver() {
        if (driver != null) {
            return driver;
        }
        System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
        DesiredCapabilities capabilities;
        capabilities = DesiredCapabilities.firefox();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("takesScreenshot", true);
        driver = chooseDriver(capabilities);
        driver.manage().timeouts().setScriptTimeout(DEFAULT_WAIT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver chooseDriver(DesiredCapabilities capabilities) {
        String preferredDriver = System.getProperty("browser", "chrome");
        boolean headless = System.getProperty("headless", "false").equals("true");

        switch (preferredDriver.toLowerCase()) {
            case "safari":
                try {
                    driver = new SafariDriver();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
                return driver;
            case "edge":
                try {
                    driver = new EdgeDriver();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
                return driver;
            case "chrome":
                final ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                try {
                    driver = new ChromeDriver(chromeOptions);
                    ErrorHandler handler = new ErrorHandler();
                    handler.setIncludeServerErrors(false);
                    //driver.setErrorHandler(handler);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
                return driver;
            case "PhantomJS":
                return new PhantomJSDriver(capabilities);
            default:
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless", "-safe-mode");
                }
                capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
                try {
                    driver = new FirefoxDriver(capabilities);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
                return driver;
        }
    }

    public static WebElement waitAndGetElementByCssSelector(WebDriver driver, String selector,
                                                            int seconds) {
        By selection = By.cssSelector(selector);
        return (new WebDriverWait(driver, seconds)).until( // ensure element is visible!
                ExpectedConditions.visibilityOfElementLocated(selection));
    }

    public static void closeDriver() {
        if (driver != null) {
            try {
                driver.close();
                driver.quit(); // fails in current geckodriver! TODO: Fixme
            } catch (NoSuchMethodError nsme) { // in case quit fails
            } catch (NoSuchSessionException nsse) { // in case close fails
            } catch (SessionNotCreatedException snce) {
            } // in case close fails
            driver = null;
        }
    }
}
