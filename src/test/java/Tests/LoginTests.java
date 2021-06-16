package Tests;

import Base.BaseTest;
import Pages.LoginPages;
import Pages.ProductsPage;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.InputStream;
import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
    LoginPages loginPages;
    ProductsPage productsPage;
    InputStream datais;

    @BeforeClass
    public void beforeClass()
    {

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
    public void invalidUserName() throws InterruptedException {
        loginPages.enterUserName(getDataBach().getJSONObject("invalidUser").getString("username"));
        loginPages.enterPassword(getDataBach().getJSONObject("invalidUser").getString("password"));
        productsPage = loginPages.pressLoginBtn();


        String actualProductTitle = productsPage.getTitle();
        String expectedProductTitle = getStrings().get("invalid_userName");

        Assert.assertEquals(actualProductTitle,expectedProductTitle);


    }
    @Test
    public void invalidPassword() throws InterruptedException {
        loginPages.enterUserName(getDataBach().getJSONObject("invalidPassword").getString("username"));
        loginPages.enterPassword(getDataBach().getJSONObject("invalidPassword").getString("password"));
        productsPage = loginPages.pressLoginBtn();

        String actualProductTitle = productsPage.getTitle();
        String expectedProductTitle = getStrings().get("invalid_passWord");

        Assert.assertEquals(actualProductTitle,expectedProductTitle);


    }
    @Test
    public void sucessfulLogin()
    {
        try{
            loginPages.enterUserName(getDataBach().getJSONObject("validUser").getString("username"));
            loginPages.enterPassword(getDataBach().getJSONObject("validUser").getString("password"));

            productsPage = loginPages.pressLoginBtn();

            System.out.println(getStrings().get("success"));
            String actualProductTitle = productsPage.getTitle();
            String expectedProductTitle = getStrings().get("product_title");

            Assert.assertEquals(actualProductTitle,expectedProductTitle);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }

    }


}
