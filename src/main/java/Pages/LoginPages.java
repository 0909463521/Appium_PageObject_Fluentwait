package Pages;
import Base.BaseTest;
import Utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
public class LoginPages extends BaseTest {
//    MobileElement usernameTxtFld = (MobileElement) driver.fi
    @AndroidFindBy (id = "userName") private MobileElement usernameTxtFld;
    @AndroidFindBy (id = "passWord") private MobileElement passwordTxtFld;
    @AndroidFindBy (id = "button") private MobileElement loginBtn;


    public LoginPages enterUserName(String username) {
        clear(usernameTxtFld);
        sendKeys(usernameTxtFld,username);
        return this;
    }
    public LoginPages enterPassword(String password) {
        clear(passwordTxtFld);
        sendKeys(passwordTxtFld,password);
        return this;
    }
    public ProductsPage pressLoginBtn() {
        click(loginBtn);
        return new ProductsPage();
    }





}

