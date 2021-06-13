package Base;

import Utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {
//    protected  static AppiumDriver driver;
//    protected  static Properties props;
//    InputStream inputStream;
//    protected  static HashMap<String,String> strings = new HashMap<String,String>();
//    InputStream inputMessage;
//    TestUtils testUtils;

    protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();
    protected static ThreadLocal <HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
    protected static ThreadLocal <JSONObject> dataBach = new ThreadLocal<JSONObject>();

    protected static ThreadLocal <String> platform = new ThreadLocal<String>();
    protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();
    protected static ThreadLocal <String> deviceName = new ThreadLocal<String>();


    private static AppiumDriverLocalService server;
    TestUtils testUtils = new TestUtils();

    public AppiumDriver getDriver() {
        return driver.get();
    }

    public void setDriver(AppiumDriver driver2) {
        driver.set(driver2);
    }


    public Properties getProps() {
        return props.get();
    }

    public void setProps(Properties props2) {
        props.set(props2);
    }

    public JSONObject getDataBach() {
        return dataBach.get();
    }

    public void setDataBach(JSONObject dataBach2) {
        dataBach.set(dataBach2);
    }

    public HashMap<String, String> getStrings() {
        return strings.get();
    }

    public void setStrings(HashMap<String, String> strings2) {
        strings.set(strings2);
    }

    public String getPlatform() {
        return platform.get();
    }

    public void setPlatform(String platform2) {
        platform.set(platform2);
    }

    public String getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(String dateTime2) {
        dateTime.set(dateTime2);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }

    public BaseTest()
    {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()),this);
        System.out.println("bach Map: " + getClass().getSimpleName());
    }

    @BeforeMethod
    public void beforeMethod() {
        ((CanRecordScreen) getDriver()).startRecordingScreen();
    }

    //stop video capturing and create *.mp4 file
    @AfterMethod
    public synchronized void afterMethod(ITestResult result) throws Exception {
        String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();

        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
        String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName")
                + File.separator + getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();

        File videoDir = new File(dirPath);

        synchronized(videoDir){
            if(!videoDir.exists()) {
                videoDir.mkdirs();
            }
        }
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
            stream.write(Base64.decodeBase64(media));
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(stream != null) {
                stream.close();
            }
        }
    }
    @Parameters({"emulator", "platformName", "udid", "deviceName", "systemPort",
            "chromeDriverPort", "wdaLocalPort", "webkitDebugProxyPort"})
    @BeforeTest
    public void beforeTest(@Optional("androidOnly")String emulator, String platformName, String udid, String deviceName,
                           @Optional("androidOnly")String systemPort, @Optional("androidOnly")String chromeDriverPort,
                           @Optional("iOSOnly")String wdaLocalPort, @Optional("iOSOnly")String webkitDebugProxyPort) throws IOException {



            setDateTime(testUtils.dateTime());
            setPlatform(platformName);
            setDeviceName(deviceName);
            URL url;

            InputStream inputStream = null;
            InputStream stringsis = null;
            InputStream datais = null;


            Properties props = new Properties();
            JSONObject loginUsers;
            AppiumDriver driver;

            try {
                props = new Properties();
                String propFileName = "config.properties";
                String xmlFileName = "strings/messageandexpectedValue.xml";
                String dataFileName = "data/loginUsers.json";

                //Props
                inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                props.load(inputStream);
                setProps(props);

                //Strings
                stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
                setStrings(testUtils.parseStringXML(stringsis));

                //Data
                datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
                JSONTokener tokener = new JSONTokener(datais);
                loginUsers = new JSONObject(tokener);
                setDataBach(loginUsers);


                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability("platformName", platformName);
                desiredCapabilities.setCapability("deviceName", deviceName);
                desiredCapabilities.setCapability("udid", udid);
                url = new URL(props.getProperty("appiumURL"));

                switch (platformName) {
                    case "Android":
                        desiredCapabilities.setCapability("automationName", props.getProperty("androidAutomationName"));
                        desiredCapabilities.setCapability("appPackage", props.getProperty("AndroidappPackage"));
                        desiredCapabilities.setCapability("appActivity", props.getProperty("AndroidappActivity"));
                        if (emulator.equalsIgnoreCase("true")) {
                            desiredCapabilities.setCapability("avd", deviceName);
                            desiredCapabilities.setCapability("avdLaunchTimeout", 120000);
                        }
                        desiredCapabilities.setCapability("systemPort", systemPort);
                        desiredCapabilities.setCapability("chromeDriverPort", chromeDriverPort);
                        String androidAppUrl = getClass().getResource(props.getProperty("Application")).getFile();

                        desiredCapabilities.setCapability("app", androidAppUrl);

                        driver = new AndroidDriver(url, desiredCapabilities);
                        break;
                    case "iOS":
                        desiredCapabilities.setCapability("automationName", props.getProperty("iOSAutomationName"));
                        String iOSAppUrl = getClass().getResource(props.getProperty("IOSapplication")).getFile();

//                        desiredCapabilities.setCapability("bundleId", props.getProperty("iOSBundleId"));
                        desiredCapabilities.setCapability("wdaLocalPort", wdaLocalPort);
                        desiredCapabilities.setCapability("webkitDebugProxyPort", webkitDebugProxyPort);
//                        desiredCapabilities.setCapability("app", iOSAppUrl);

                        driver = new IOSDriver(url, desiredCapabilities);
                        break;
                    default:
                        throw new Exception("Invalid platform! - " + platformName);
                }
                setDriver(driver);


            } catch (Exception e) {
                e.printStackTrace();


            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (stringsis != null) {
                    stringsis.close();
                }
            }

    }
    public void waitForVisibility(MobileElement element){
        WebDriverWait wait  = new WebDriverWait(getDriver(), TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void waitForVisibility(WebElement e){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.visibilityOf(e));
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
        getDriver().quit();
    }

}
