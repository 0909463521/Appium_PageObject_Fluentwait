package Base;

import Utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected  static AppiumDriver driver;
    protected  static Properties props;
    InputStream inputStream;
    protected  static HashMap<String,String> strings = new HashMap<String,String>();
    InputStream inputMessage;
    TestUtils testUtils;

    public BaseTest()
    {
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
        System.out.println("bach Map: " + getClass().getSimpleName());
    }
    @Parameters({"platformName","platformVersion","deviceName"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion , String deviceName ) throws IOException {
        try{

            props = new Properties();
            String Stringproperties = "config.properties";
            String messageFileName = "strings/messageandexpectedValue.xml";

            inputStream = getClass().getClassLoader().getResourceAsStream(Stringproperties);
            props.load(inputStream);

            inputMessage = getClass().getClassLoader().getResourceAsStream(messageFileName);
            testUtils = new TestUtils();
            strings = testUtils.parseStringXML(inputMessage);

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("sessionName", "Automation test session");
            capabilities.setCapability("sessionDescription", "");
            capabilities.setCapability("deviceOrientation", "portrait");
            capabilities.setCapability("noReset", true);
            capabilities.setCapability("fullReset", false);
            capabilities.setCapability("captureScreenshots", true);

            capabilities.setCapability("deviceName", deviceName);

            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("platformName", platformName);
            capabilities.setCapability("udid", "R58MB0T56ZA");

            capabilities.setCapability("appPackage", props.getProperty("AndroidappPackage"));
            capabilities.setCapability("appActivity", props.getProperty("AndroidappActivity"));
            String androidAppUrl = getClass().getResource(props.getProperty("Application")).getFile();
            capabilities.setCapability("app", androidAppUrl);

            System.out.println(props.getProperty("Application"));

            URL url = new URL("http://127.0.0.1:4723/wd/hub");
            driver = new AndroidDriver(url,capabilities);
            String sessionId = driver.getSessionId().toString();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(inputStream != null )
            {
                inputStream.close();
            }
            if(inputMessage != null )
            {
                inputMessage.close();
            }
        }


    }
    public void waitForVisibility(MobileElement element){
        WebDriverWait wait  = new WebDriverWait(driver, TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void click(MobileElement element)
    {
        waitForVisibility(element);
        element.click();
    }
    public void sendKeys(MobileElement element, String text)
    {
        waitForVisibility(element);
        element.sendKeys(text);
    }
    public String getAttribute(MobileElement element, String attribute)
    {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    @AfterTest
    public void afterTest()
    {
        driver.quit();
    }

}
