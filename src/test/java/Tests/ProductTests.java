package Tests;

import Base.BaseTest;
import Pages.LoginPages;
import Pages.ProductsPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.BeforeClass;

import java.io.InputStream;

public class ProductTests extends BaseTest {
    LoginPages loginPage;
    ProductsPage productsPage;
    JSONObject loginUsers;
    @BeforeClass
    public void beforeClass() throws Exception {
        InputStream datais = null;
        try {
            String dataFileName = "data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(datais != null) {
                datais.close();
            }
        }

    }
}
