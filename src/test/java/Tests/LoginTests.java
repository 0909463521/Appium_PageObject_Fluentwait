package Tests;

import Base.BaseTest;
import Pages.LoginPages;
import Pages.ProductsPage;
import Utils.TestUtils;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonToken;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class LoginTests extends BaseTest {
    LoginPages loginPages;
    ProductsPage productsPage;
    InputStream datais;
    JSONObject LoginUsers;
    @BeforeClass
    public void beforeClass()
    {

        try {
            String dataFileName = "/Users/bachvu/Desktop/PageObject/src/main/resources/data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream("loginUsers.json");

            JSONTokener jsonTokener = new JSONTokener(datais);
            LoginUsers = new JSONObject(jsonTokener);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {

        }
    }
    @BeforeMethod
    public void beforeMethod(Method method)
    {
      try {
          loginPages = new LoginPages();
          System.out.println("Start: "+method.getName());


      }
      catch (Exception e)
      {

      }finally {

      }

    }
    @AfterMethod
    public void afterMethod()
    {

    }
    @Test
    public void sucessfulLogin()
    {
        try{
            loginPages.enterUserName(LoginUsers.getJSONObject("validUser").getString("username"));
            loginPages.enterPassword(LoginUsers.getJSONObject("validUser").getString("password"));
            loginPages.pressStandard_user();
            productsPage = loginPages.pressLoginBtn();

            System.out.println(strings.get("success"));
            String actualProductTitle = productsPage.getTitle();
            String expectedProductTitle = strings.get("product_title");

            Assert.assertEquals(actualProductTitle,expectedProductTitle);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail(e.toString());
        }




    }


}
