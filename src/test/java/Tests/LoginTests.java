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
    JSONObject LoginUsers;
    @BeforeClass
    public void beforeClass()
    {

        try {
            String dataFileName = "data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);

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
    public void invalidUserName() {
        loginPages.enterUserName(LoginUsers.getJSONObject("invalidUser").getString("username"));
        loginPages.enterPassword(LoginUsers.getJSONObject("invalidUser").getString("password"));
        loginPages.pressLoginBtn();




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
